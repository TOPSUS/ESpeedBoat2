package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyAdapter.MetodePembayaranAdapter;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FeriFragment;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MetodePembayaran.ServerResponseMetodePembayaranData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelianData;
import id.alin.espeedboat.MyRetrofit.Services.PemesananServices;
import id.alin.espeedboat.MyRoom.Entity.MetodePembayaranEntity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
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

    // TIPE_KAPAL
    private String tipe_kapal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

        // MENGAMBIL TIPE_KAPAL
        this.tipe_kapal = getIntent().getStringExtra(FeriFragment.TIPE_KAPAL);

        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

        Gson gson = new Gson();
        String jsonPenumpang = gson.toJson(penumpangDataList);

        Log.d("alin_debug",jsonPenumpang);

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
    private void postPemesananJadwalSpeedboatAPI(String token, String id_pemesan, String id_jadwal,String tanggal_berangkat,
                                                 String id_metode_pembayaran, String jsonPenumpang,
                                                 String tipe_kapal) {
        Log.d("jsonpemesanan",jsonPenumpang);
        setStateLoading();

        PemesananServices services = ApiClient.getRetrofit().create(PemesananServices.class);
        Call<ServerResponsePembelianData> call = services.postPemesananTicket(
                token,
                id_pemesan,
                id_jadwal,
                tanggal_berangkat,
                id_metode_pembayaran,
                jsonPenumpang,
                tipe_kapal
        );

        call.enqueue(new Callback<ServerResponsePembelianData>() {
            @Override
            public void onResponse(Call<ServerResponsePembelianData> call, Response<ServerResponsePembelianData> response) {
                if(response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")){
                    Intent intent = new Intent(MetodePembayaranActivity.this, MyUnpaidDetailTransactionActivity.class);
                    intent.putExtra("id_trans", response.body().getPembelian().getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MainActivity.mainActivityViewModel.setPemesananSpeedboatData(new PemesananSpeedboatData());
                    finishAffinity();
                }else{
                    Toast.makeText(MetodePembayaranActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    setStateReady();
                }
            }

            @Override
            public void onFailure(Call<ServerResponsePembelianData> call, Throwable t) {
                Toast.makeText(MetodePembayaranActivity.this, "GAGAL : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                setStateReady();
            }
        });
    }

    // POST PEMESANAN UNTUK FERI + KENDARAAN
    private void postPemesananFeriKendaraanAPI(String token, String id_pemesan, String id_jadwal,String tanggal_berangkat,
                                               String id_metode_pembayaran, String jsonPenumpang,
                                               String tipe_kapal,String nomor_polisi,String id_golongan){
        Log.d("jsonpemesanan",jsonPenumpang);
        setStateLoading();

        PemesananServices services = ApiClient.getRetrofit().create(PemesananServices.class);
        Call<ServerResponsePembelianData> call = services.postPemesananTicket(
                token,
                id_pemesan,
                id_jadwal,
                tanggal_berangkat,
                id_metode_pembayaran,
                jsonPenumpang,
                tipe_kapal,
                id_golongan,
                nomor_polisi
        );

        call.enqueue(new Callback<ServerResponsePembelianData>() {
            @Override
            public void onResponse(Call<ServerResponsePembelianData> call, Response<ServerResponsePembelianData> response) {
                if(response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")){
                    Intent intent = new Intent(MetodePembayaranActivity.this, MyUnpaidDetailTransactionActivity.class);
                    intent.putExtra("id_trans", response.body().getPembelian().getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MainActivity.mainActivityViewModel.setPemesananFeriData(new PemesananFeriData());
                    finishAffinity();
                }else{
                    Toast.makeText(MetodePembayaranActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    setStateReady();
                }
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
                        // MEMANGGIL POST PEMSANAN JADWAL API UNTUK SPEEDBOAT
                        if(MetodePembayaranActivity.this.tipe_kapal.matches(PemesananJadwalSpeedboatActivity.SPEEDBOAT)){
                            TransaksiData transaksiData = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
                            ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
                            List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
                            PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();

                            String token = profileData.getToken();
                            String id_pemesan = profileData.getUser_id();
                            String id_jadwal = String.valueOf(transaksiData.getId_jadwal());
                            String id_metode_pembayaran = String.valueOf(transaksiData.getId_metode_pembayaran());
                            String tanggal_berangkat = pemesananSpeedboatData.getTanggal_variable();
                            Gson gson = new Gson();
                            String jsonPenumpang = gson.toJson(penumpangDataList);

                            postPemesananJadwalSpeedboatAPI(token,id_pemesan,id_jadwal,tanggal_berangkat,id_metode_pembayaran,jsonPenumpang,PemesananJadwalSpeedboatActivity.SPEEDBOAT);
                        }else{
                            TransaksiData transaksiData = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
                            ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
                            PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();

                            List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

                            String token = profileData.getToken();
                            String id_pemesan = profileData.getUser_id();
                            String id_jadwal = String.valueOf(transaksiData.getId_jadwal());
                            String id_metode_pembayaran = String.valueOf(transaksiData.getId_metode_pembayaran());
                            String tanggal_berangkat = pemesananFeriData.getTanggal_variable();
                            Gson gson = new Gson();
                            String jsonPenumpang = gson.toJson(penumpangDataList);
                            String nomor_polisi = pemesananFeriData.getNomor_kendaraan();
                            String id_golongan = String.valueOf(pemesananFeriData.getId_golongan_kendaraan());

                            if(pemesananFeriData.getTipe_jasa().matches(FeriFragment.KENDARAAN)){
                                postPemesananFeriKendaraanAPI(token,id_pemesan,id_jadwal,tanggal_berangkat,id_metode_pembayaran,jsonPenumpang,
                                                                    tipe_kapal,nomor_polisi,id_golongan);
                            }else{
                                postPemesananJadwalSpeedboatAPI(token,id_pemesan,id_jadwal,tanggal_berangkat,id_metode_pembayaran,jsonPenumpang,PemesananJadwalSpeedboatActivity.FERI);
                            }
                        }
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