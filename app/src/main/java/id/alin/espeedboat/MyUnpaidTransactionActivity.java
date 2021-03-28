package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyUnpaidTransactionAdapter;
import id.alin.espeedboat.MyAdapter.PelabuhanExpandedAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal.ServerResponseJadwalData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan.ServerResponsePelabuhanData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import id.alin.espeedboat.MyRetrofit.Services.PelabuhanServices;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyUnpaidTransactionActivity extends AppCompatActivity {
    String status = "menunggu pembayaran";
    private LinearLayout loading, nodata;
    ImageButton btnBack;
    MyUnpaidTransactionAdapter myUnpaidTransactionAdapter;
    RecyclerView recyclerView;
    TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_unpaid_transaction);
        init();
        setStateLoading();
        getDataTransaksiFromAPI();
    }

    private void init(){
        tvHeader = (TextView) findViewById(R.id.tvHeaderTransaksi);
        if(getIntent().getStringExtra("status").equals("menunggu pembayaran")){
            status = getIntent().getStringExtra("status");
            tvHeader.setText("Transaksi Belum Dibayar");
        }else if(getIntent().getStringExtra("status").equals("terkonfirmasi")){
            status = getIntent().getStringExtra("status");
            tvHeader.setText("Transaksi Terkonfirmasi");
        }else if(getIntent().getStringExtra("status").equals("done")){
            status = getIntent().getStringExtra("status");
            tvHeader.setText("Riwayat Transaksi");
        }

        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        btnBack = (ImageButton) findViewById(R.id.backButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUnpaidTransaction);

        //BACK LISTENER
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*MENGAMBIL DATA PEMBELIAN DARI API PEMBELIAN*/
    private void getDataTransaksiFromAPI(){

        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponsePembelian> call = pembelianServices.getPembelian(
                authorization,
                status
        );

        call.enqueue(new Callback<ServerResponsePembelian>() {
            @Override
            public void onResponse(Call<ServerResponsePembelian> call, Response<ServerResponsePembelian> response) {
                Log.d("STATE",String.valueOf(response.body().getMessage()));
                    if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                        if(response.body().getPembelian().size() > 0){
                            setStateReady();
                            Log.d("STATE",String.valueOf(response.body().getPembelian().size()));
                            setRecyclerView(response.body().getPembelian());
                        }else{
                            setStateNodata();
                        }
                    }else{
                        setStateNodata();
                        Toast.makeText(MyUnpaidTransactionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<ServerResponsePembelian> call, Throwable t) {
                setStateNodata();
            }
        });
    }

    //SET RECYLERVIEW
    private void setRecyclerView(List<PembelianEntitiy> pembelianEntity) {
        myUnpaidTransactionAdapter = new MyUnpaidTransactionAdapter(pembelianEntity, MyUnpaidTransactionActivity.this);
        recyclerView.setAdapter(myUnpaidTransactionAdapter);
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