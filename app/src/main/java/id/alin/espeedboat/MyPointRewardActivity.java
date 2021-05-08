package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointAdapter;
import id.alin.espeedboat.MyAdapter.MyPointRedeemAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Poin.ServerResponsePoin;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseReward;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import id.alin.espeedboat.MySharedPref.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPointRewardActivity extends AppCompatActivity {

    private long id;
    private int position;

    private LinearLayout loading, nodata;
    private TextView tvNamaKapal, tvPoin;
    MyPointRedeemAdapter myPointRedeemAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<String> speedboat;
    ArrayList<String> point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point_reward);
        init();
    }

    private void init(){
        if (getIntent().hasExtra("id_trans")) {
            id = getIntent().getLongExtra("id_trans", 3);
            position = getIntent().getIntExtra("position", 0);
        }

        //LL INIT
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);

        //TV INIT
        tvNamaKapal = (TextView) findViewById(R.id.tvnamaSpeedboatPointRedeem);
        tvPoin = (TextView) findViewById(R.id.tvtotalSpeedboatPoinRedeem);
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMyPointRedeem);
        getDataRewardFromAPI();
    }

    /*MENGAMBIL DATA PEMBELIAN DARI API PEMBELIAN*/
    private void getDataRewardFromAPI(){

        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseReward> call = pembelianServices.postReward(
                authorization,
                id
        );

        call.enqueue(new Callback<ServerResponseReward>() {
            @Override
            public void onResponse(Call<ServerResponseReward> call, Response<ServerResponseReward> response) {
                Log.d("STATE",String.valueOf(response.body().getMessage()));
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    tvNamaKapal.setText(response.body().getNama_kapal());
                    tvPoin.setText(String.valueOf(response.body().getTotal_poin()));
                    if(response.body().getRewards().size() > 0){
                        setStateReady();
                        Log.d("STATE",String.valueOf(response.body().getRewards().size()));
                        myPointRedeemAdapter = new MyPointRedeemAdapter(response.body().getRewards(), response.body().getTotal_poin(), sharedPreferences,MyPointRewardActivity.this);
                        recyclerView.setAdapter(myPointRedeemAdapter);
                    }else{
                        setStateNodata();
                    }
                }else{
                    setStateNodata();
                    Toast.makeText(MyPointRewardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseReward> call, Throwable t) {
                setStateNodata();
            }
        });
    }

    /*DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0*/
    private void setStateReady(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    /*STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading(){
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    /*STATE NO DATA SAAT DATA YANG DIBERIKAN OLEH SERVER KOSONG*/
    private void setStateNodata(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStateLoading();
        getDataRewardFromAPI();
    }
}