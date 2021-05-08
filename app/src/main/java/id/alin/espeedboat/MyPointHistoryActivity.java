package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointHistoryAdapter;
import id.alin.espeedboat.MyAdapter.MyPointRedeemAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseReward;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseRiwayatReward;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyPointHistoryActivity extends AppCompatActivity {

    private LinearLayout loading, nodata;
    RecyclerView recyclerView;
    ImageButton backBtn;
    MyPointHistoryAdapter myPointHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point_history);

        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMyPointHistory);
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        backBtn = (ImageButton) findViewById(R.id.backButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setStateLoading();
        postRiwayatFromApi();
    }

    private void postRiwayatFromApi(){
        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseRiwayatReward> call = pembelianServices.riwayatReward(
                authorization,
                5
        );

        call.enqueue(new Callback<ServerResponseRiwayatReward>() {
            @Override
            public void onResponse(Call<ServerResponseRiwayatReward> call, Response<ServerResponseRiwayatReward> response) {
                Log.d("STATE",String.valueOf(response.body().getMessage()));
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    if(response.body().getRiwayat().size() > 0){
                        setStateReady();
                        Log.d("STATE",String.valueOf(response.body().getRiwayat().size()));
                        myPointHistoryAdapter = new MyPointHistoryAdapter(response.body().getRiwayat(), MyPointHistoryActivity.this);
                        recyclerView.setAdapter(myPointHistoryAdapter);
                    }else{
                        setStateNodata();
                    }
                }else{
                    setStateNodata();
                    Toast.makeText(MyPointHistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseRiwayatReward> call, Throwable t) {
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
}