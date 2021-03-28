package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyUnpaidTransactionAdapter;
import id.alin.espeedboat.MyAdapter.PenumpangDetailAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian.ServerResponseDetailPembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyRoom.Entity.PenumpangDetailEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class MyUnpaidDetailTransactionActivity extends AppCompatActivity {
    private long id = 0;
    private long timeLeft = 60000;

    private NestedScrollView detail;
    private LinearLayout loading, nodata, layoutBelumDibayar, layoutMenungguKonfirmasi, layoutTerkonfirmasi;
    CountDownTimer countDownTimer;
    TextView tvStatusBayar, tvCountDown, tvKapal, tvTanggal, tvHarga, tvAsal, tvTujuan, tvBerangkat, tvSampai, tvNamaPemesan, tvEmailPemesan, tvTeleponPemesan;
    Button btnUploadBukti, btnBatalPesan;
    ImageButton btnBack;
    RecyclerView recyclerView;
    PenumpangDetailAdapter penumpangDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_unpaid_detail_transaction);
        init();
        setStateLoading();
        countDown();
    }

    private void init(){
        if(getIntent().hasExtra("id_trans")){
            id = getIntent().getLongExtra("id_trans", 3);
            Log.d("jajaja", String.valueOf(id));

        }

        btnBack = (ImageButton) findViewById(R.id.backButton);
        detail = (NestedScrollView) findViewById(R.id.nestedDetail);
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        layoutBelumDibayar = (LinearLayout) findViewById(R.id.layoutBelumDibayar);
        layoutMenungguKonfirmasi = (LinearLayout) findViewById(R.id.layoutMenungguKonfirmasi);
        layoutTerkonfirmasi = (LinearLayout) findViewById(R.id.layoutTerkonfirmasi);

        recyclerView = (RecyclerView) findViewById(R.id.rvPenumpang);
        btnUploadBukti = (Button) findViewById(R.id.btnUploadBukti);
        btnBatalPesan = (Button) findViewById(R.id.btnBatalPesan);

        tvStatusBayar = (TextView) findViewById(R.id.tvStatusBayar);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvKapal = (TextView) findViewById(R.id.tvJenisKapalUnpaid);
        tvTanggal = (TextView) findViewById(R.id.tvTanggalUnpaid);
        tvHarga = (TextView) findViewById(R.id.tvHargaUnpaid);
        tvAsal = (TextView) findViewById(R.id.tvAsalUnpaid);
        tvTujuan = (TextView) findViewById(R.id.tvTujuanUnpaid);
        tvBerangkat = (TextView) findViewById(R.id.tvWaktuAsalUnpaid);
        tvSampai = (TextView) findViewById(R.id.tvWaktuTujuanUnpaid);

        tvNamaPemesan = (TextView) findViewById(R.id.namapemesan);
        tvEmailPemesan = (TextView) findViewById(R.id.emailpemesan);
        tvTeleponPemesan = (TextView) findViewById(R.id.nomorhppemesan);

        postDetailPesananFromApi();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void postDetailPesananFromApi(){
        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseDetailPembelian> call = pembelianServices.getDetailPembelian(
                authorization,
                id
        );

        call.enqueue(new Callback<ServerResponseDetailPembelian>() {
            @Override
            public void onResponse(Call<ServerResponseDetailPembelian> call, Response<ServerResponseDetailPembelian> response) {
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    Log.d("hahe", String.valueOf(response.body().getPenumpang().size()));
                    setRecyclerView(response.body().getPenumpang());
                    setData(response.body());
                    setStateReady();
                }else{
                    setStateNodata();
                    Toast.makeText(MyUnpaidDetailTransactionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseDetailPembelian> call, Throwable t) {
                setStateNodata();
            }
        });
    }

    private void setData(ServerResponseDetailPembelian body){
        //INGET STATUS BUAT LAST SAMA COUNTDOWN BOSSS!!!!
        //FORMAT HARGA
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String total_biaya_rupiah = "IDR " + kursIndonesia.format(body.getHarga());

        //FORMAT TANGGAL
        String tanggal[] = body.getTanggal().split("-", 3);
        String day = tanggal[2];
        String month = tanggal[1];
        String year = tanggal[0];

        if(month.equals("01")){
            month = "Januari";
        }else if(month.equals("02")) {
            month = "Februari";
        }else if(month.equals("03")){
            month = "Maret";
        }else if(month.equals("04")){
            month = "April";
        }else if(month.equals("05")){
            month = "Mei";
        }else if(month.equals("06")){
            month = "Juni";
        }else if(month.equals("07")){
            month = "Juli";
        }else if(month.equals("08")){
            month = "Agustus";
        }else if(month.equals("09")){
            month = "September";
        }else if(month.equals("10")){
            month = "Oktober";
        }else if(month.equals("11")){
            month = "November";
        }else if(month.equals("12")){
            month = "Desember";
        }


        tvKapal.setText(body.getKapal());
        tvHarga.setText(total_biaya_rupiah);
        tvTanggal.setText(day + " " + month + " " + year);
        tvAsal.setText(body.getPelabuhan_asal());
        tvTujuan.setText(body.getPelabuhan_tujuan());
        tvBerangkat.setText(body.getWaktu_berangkat().substring(0, Math.min(body.getWaktu_berangkat().length(), 5))+ " WITA");
        tvSampai.setText(body.getWaktu_sampai().substring(0, Math.min(body.getWaktu_sampai().length(), 5))+ " WITA");

        tvNamaPemesan.setText(body.getNama_pemesan());
        tvEmailPemesan.setText("Email: " + body.getEmail_pemesan());
        tvTeleponPemesan.setText("Telepon: " + body.getTelepon_pemesan());

        //SWITCH LAYOUT BY STATUS
        if (body.getStatus_transaksi().equals("menunggu pembayaran")){
            tvStatusBayar.setText("Belum Dibayar");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Danger_Red));
            layoutMenungguKonfirmasi.setVisibility(View.INVISIBLE);
            layoutBelumDibayar.setVisibility(View.VISIBLE);
            layoutTerkonfirmasi.setVisibility(View.INVISIBLE);
        }else if (body.getStatus_transaksi().equals("menunggu konfirmasi")){
            tvStatusBayar.setText("Menunggu Konfirmasi");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Warning_Orange));
            tvCountDown.setVisibility(View.INVISIBLE);
            layoutMenungguKonfirmasi.setVisibility(View.VISIBLE);
            layoutBelumDibayar.setVisibility(View.INVISIBLE);
            layoutTerkonfirmasi.setVisibility(View.INVISIBLE);
        }else if (body.getStatus_transaksi().equals("terkonfirmasi")){
            tvStatusBayar.setText("Dibayar");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Safety_Green));
            tvCountDown.setVisibility(View.INVISIBLE);
            layoutMenungguKonfirmasi.setVisibility(View.INVISIBLE);
            layoutBelumDibayar.setVisibility(View.INVISIBLE);
            layoutTerkonfirmasi.setVisibility(View.VISIBLE);
        }else if (body.getStatus_transaksi().equals("dibatalkan")){
            tvStatusBayar.setText("Dibatalkan");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Danger_Red));
            tvCountDown.setVisibility(View.INVISIBLE);
        }else if (body.getStatus_transaksi().equals("expired")){
            tvStatusBayar.setText("Kadaluwarsa");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Danger_Red));
            tvCountDown.setVisibility(View.INVISIBLE);
        }else if (body.getStatus_transaksi().equals("digunakan")){
            tvStatusBayar.setText("Selesai");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this,R.color.Safety_Green));
            tvCountDown.setVisibility(View.INVISIBLE);
        }
    }

    //KELOLA PEMBAYARAB
    //UPLOAD BUKTI PEMBAYARAN
    private void postBuktiPembayaranToApi(){

    }

    //BATALKAN PESANAN
    private void postBatalPesanToApi(){

    }

    //DOWNLOAD BUKTI PEMBAYARAN
    private void postBuktiPembayaranFromApi(){

    }

    //DOWNLOAD TIKET
    private void postTiketFromApi(){

    }

    //SET RECYLERVIEW
    private void setRecyclerView(List<PenumpangDetailEntity> penumpangDetailEntities) {
        penumpangDetailAdapter = new PenumpangDetailAdapter(MyUnpaidDetailTransactionActivity.this, penumpangDetailEntities);
        recyclerView.setAdapter(penumpangDetailAdapter);
    }

    /*DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0*/
    private void setStateReady(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.VISIBLE);
    }

    /*STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading(){
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.INVISIBLE);
    }

    /*STATE NO DATA SAAT DATA YANG DIBERIKAN OLEH SERVER KOSONG*/
    private void setStateNodata(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }

    private void countDown(){
        countDownTimer = new CountDownTimer(timeLeft, 10){
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void updateTimer(){
        int menit = (int) timeLeft/60000;
        int detik = (int) timeLeft%60000/1000;

        String timeLeft;
        timeLeft = "Batas Waktu: " + menit + " mnt ";
        timeLeft = timeLeft + detik + " dtk";
        if(menit==0 && detik==0){
            tvCountDown.setVisibility(View.GONE);
            tvStatusBayar.setText("Kadaluwarsa");
            btnUploadBukti.setVisibility(View.GONE);
            btnBatalPesan.setVisibility(View.GONE);
        }else{
            tvCountDown.setText(timeLeft);
        }
    }
}