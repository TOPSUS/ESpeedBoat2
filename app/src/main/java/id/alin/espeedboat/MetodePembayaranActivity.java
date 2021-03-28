package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyAdapter.MetodePembayaranAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MetodePembayaran.ServerResponseMetodePembayaranData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelianData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.PemesananServices;
import id.alin.espeedboat.MyRoom.Entity.MetodePembayaranEntity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* CLASS INI MENGGUNAKAN VIEW MODEL ACTIVITY INPUT IDENTITAS PEMESAN ACTIVITY
* JAD KELAS INI TIDAK MEMILIKI VIEW MODEL
* */
public class MetodePembayaranActivity extends AppCompatActivity {

    /*RECYCLER VIEW*/
    private RecyclerView rvmetodepembayaran;
    private MetodePembayaranAdapter metodePembayaranAdapter;

    /*LAYOUT*/
    private LinearLayout content, nodata, loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

        initWidget();
        getMetodePembayaranFromAPI();
    }

    /*METHOD YANG DIGUNAKNA UNTUK INISIASI WIDGET PADA LAMAN ACTIVITY*/
    private void initWidget() {
        this.rvmetodepembayaran = findViewById(R.id.metodepembayaran);
        this.loading = findViewById(R.id.loadinglayout);
        this.nodata = findViewById(R.id.nodatalayout);
        this.content = findViewById(R.id.content);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.rvmetodepembayaran.setLayoutManager(layoutManager);
    }

    /*RECYCLERVIEW FILLER SETELAH MENDAPATKAN RESPONSE SUKSES DARI SERVER*/
    private void fillRecyclerView(List<MetodePembayaranEntity> metodePembayaranEntities){
        this.metodePembayaranAdapter = new MetodePembayaranAdapter(this, metodePembayaranEntities);
        this.rvmetodepembayaran.setAdapter(this.metodePembayaranAdapter);
    }

    /*METHOD UNTUK MENGAMBIL METODE PEMBAYARAN YANG TERSEDIA DARI SERVER*/
    private void getMetodePembayaranFromAPI(){
        setStateLoading();
        PemesananServices pemesananServices = ApiClient.getRetrofit().create(PemesananServices.class);

        /*GET PROFILE DATA FROM VIEW MODEL*/
        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        Call<ServerResponseMetodePembayaranData> call = pemesananServices.getMetodePembayaran(
                profileData.getToken()
        );

        call.enqueue(new Callback<ServerResponseMetodePembayaranData>() {
            @Override
            public void onResponse(Call<ServerResponseMetodePembayaranData> call, Response<ServerResponseMetodePembayaranData> response) {
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    /*FILL RECYCLER VIEW*/
                    fillRecyclerView(response.body().getMetodePembayaranEntities());
                    setStateReady();
                }else{
                    Toast.makeText(MetodePembayaranActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    setStateNodata();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseMetodePembayaranData> call, Throwable t) {
                Toast.makeText(MetodePembayaranActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                setStateNodata();
            }
        });
    }

    /*METHOD YANG DIGUNAKAN UNTUK MENGIRIMKAN DATA PEMESANAN KE SERVER*/
    private void postPemesananJadwalAPI(String token, String id_pemesan, String id_jadwal, String id_metode_pembayaran, String jsonPenumpang) {
        Log.d("jsonpemesanan",jsonPenumpang);
        setStateLoading();

        PemesananServices services = ApiClient.getRetrofit().create(PemesananServices.class);
        Call<ServerResponsePembelianData> call = services.postPemesananTicket(
                token,
                id_pemesan,
                id_jadwal,
                id_metode_pembayaran,
                jsonPenumpang
        );

        call.enqueue(new Callback<ServerResponsePembelianData>() {
            @Override
            public void onResponse(Call<ServerResponsePembelianData> call, Response<ServerResponsePembelianData> response) {
                if(response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")){
                    Toast.makeText(MetodePembayaranActivity.this, String.valueOf(response.body().getPembelian().getId()), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MetodePembayaranActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                setStateReady();
            }

            @Override
            public void onFailure(Call<ServerResponsePembelianData> call, Throwable t) {
                Toast.makeText(MetodePembayaranActivity.this, "GAGAL : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                setStateReady();
            }
        });
    }

    /*SET STATE LOADING*/
    private void setStateLoading(){
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.content.setVisibility(View.INVISIBLE);
    }

    /*SET STATE NO DATA*/
    private void setStateNodata(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
        this.content.setVisibility(View.INVISIBLE);
    }

    /*SET STATE READY*/
    private void setStateReady(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.content.setVisibility(View.VISIBLE);
    }

    /*MENAMPILKAN DIALOG PEMILIHAN METODE PEMBAYARAN*/
    public void showModalPersetujuanMetodePembayaran(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("YAKIN MENGGUNAKAN METODE PEMBAYARAN INI ?")
                .setMessage("Metode pembayaran ini akan segera dibentuk setelah anda menekan lanjutkan, ticket sejumlah pesanan anda juga akan di booking oleh sistem")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("LANJUTKAN", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        /*MEMANGGIL POST PEMSANAN JADWAL API*/
                        TransaksiData transaksiData = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
                        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
                        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

                        String token = profileData.getToken();
                        String id_pemesan = profileData.getUser_id();
                        String id_jadwal = String.valueOf(transaksiData.getId_jadwal());
                        String id_metode_pembayaran = String.valueOf(transaksiData.getId_metode_pembayaran());

                        Gson gson = new Gson();
                        String jsonPenumpang = gson.toJson(penumpangDataList);

                        postPemesananJadwalAPI(token,id_pemesan,id_jadwal,id_metode_pembayaran,jsonPenumpang);

                    }
                })
                .setNegativeButton("BATAL", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        TransaksiData transaksiData = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
                        Toast.makeText(MetodePembayaranActivity.this, transaksiData.getMetode_pembayaran(), Toast.LENGTH_SHORT).show();
                        transaksiData.setId_metode_pembayaran(0);
                        transaksiData.setMetode_pembayaran("");
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

}