package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Poin.ServerResponsePoin;
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

public class MyPointActivity extends AppCompatActivity {

    private LinearLayout loading, nodata;

    MyPointAdapter myPointAdapter;
    RecyclerView recyclerView;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point);
        init();
    }

    private void init(){
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMyPoint);
        backBtn = (ImageButton) findViewById(R.id.backButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setStateLoading();
        getDataPoinFromAPI();
    }

    /*MENGAMBIL DATA PEMBELIAN DARI API PEMBELIAN*/
    private void getDataPoinFromAPI() {

        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponsePoin> call = pembelianServices.postPoin(
                authorization,
                5
        );

        call.enqueue(new Callback<ServerResponsePoin>() {
            @Override
            public void onResponse(Call<ServerResponsePoin> call, Response<ServerResponsePoin> response) {
                Log.d("STATE", String.valueOf(response.body().getMessage()));
                if (response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")) {
                    if (response.body().getPoin().size() > 0) {
                        myPointAdapter = new MyPointAdapter(response.body().getPoin(), MyPointActivity.this);
                        recyclerView.setAdapter(myPointAdapter);
                        setStateReady();
                    } else {
                        setStateNodata();
                    }
                } else {
                    setStateNodata();
                    Toast.makeText(MyPointActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponsePoin> call, Throwable t) {
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
        getDataPoinFromAPI();
    }
}