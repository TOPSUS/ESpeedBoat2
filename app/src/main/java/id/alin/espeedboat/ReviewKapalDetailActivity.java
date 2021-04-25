
package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dmax.dialog.SpotsDialog;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian.ServerResponseDetailPembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Review.ServerResponseReview;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ReviewKapalDetailActivity extends AppCompatActivity {
    private long id = 0;
    private int position;
    private String state;
    int ratingKapal;

    private Dialog loadingdialog;
    private LinearLayout loading, nodata;
    RatingBar ratingBar;
    EditText etUlasanKapal;
    Button btnSubmit;
    ImageButton btnBack;
    TextView tvStatusBayar, tvCountDown, tvKapal, tvTanggal, tvHarga, tvAsal, tvTujuan, tvBerangkat, tvSampai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_kapal_detail);

        init();
        setStateLoading();
    }

    private void init(){
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        etUlasanKapal = (EditText) findViewById(R.id.etUlasanKapal);
        btnSubmit = (Button) findViewById(R.id.btnSubmitReview);
        btnBack = (ImageButton) findViewById(R.id.backButton);
        tvStatusBayar = (TextView) findViewById(R.id.tvStatusBayar);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvKapal = (TextView) findViewById(R.id.tvJenisKapalUnpaid);
        tvTanggal = (TextView) findViewById(R.id.tvTanggalUnpaid);
        tvHarga = (TextView) findViewById(R.id.tvHargaUnpaid);
        tvAsal = (TextView) findViewById(R.id.tvAsalUnpaid);
        tvTujuan = (TextView) findViewById(R.id.tvTujuanUnpaid);
        tvBerangkat = (TextView) findViewById(R.id.tvWaktuAsalUnpaid);
        tvSampai = (TextView) findViewById(R.id.tvWaktuTujuanUnpaid);


        if (getIntent().hasExtra("id_trans")) {
            id = getIntent().getLongExtra("id_trans", 3);
            position = getIntent().getIntExtra("position", 0);
            state = getIntent().getStringExtra("state");
        }

        postDetailPesananFromApi();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingKapal = (int)rating;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateLoading();
                postReviewToApi();
            }
        });
    }

    private void setData(ServerResponseDetailPembelian body) {
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

        if (month.equals("01")) {
            month = "Januari";
        } else if (month.equals("02")) {
            month = "Februari";
        } else if (month.equals("03")) {
            month = "Maret";
        } else if (month.equals("04")) {
            month = "April";
        } else if (month.equals("05")) {
            month = "Mei";
        } else if (month.equals("06")) {
            month = "Juni";
        } else if (month.equals("07")) {
            month = "Juli";
        } else if (month.equals("08")) {
            month = "Agustus";
        } else if (month.equals("09")) {
            month = "September";
        } else if (month.equals("10")) {
            month = "Oktober";
        } else if (month.equals("11")) {
            month = "November";
        } else if (month.equals("12")) {
            month = "Desember";
        }

        tvKapal.setText(body.getKapal());
        tvHarga.setText(total_biaya_rupiah);
        tvTanggal.setText(day + " " + month + " " + year);
        tvAsal.setText(body.getPelabuhan_asal());
        tvTujuan.setText(body.getPelabuhan_tujuan());
        tvBerangkat.setText(body.getWaktu_berangkat().substring(0, Math.min(body.getWaktu_berangkat().length(), 5)) + " WITA");
        tvSampai.setText(body.getWaktu_sampai().substring(0, Math.min(body.getWaktu_sampai().length(), 5)) + " WITA");
        tvStatusBayar.setText("Selesai");
        tvStatusBayar.setTextColor(ContextCompat.getColor(ReviewKapalDetailActivity.this, R.color.Safety_Green));

        if(state.equals("reviewed")){
            ratingBar.setRating(body.getRating());
            ratingBar.setIsIndicator(true);
            etUlasanKapal.setText(body.getReview());
            etUlasanKapal.setEnabled(false);
            btnSubmit.setVisibility(View.GONE);
        }

    }

    //MENGAMBIL DETAIL PESANAN
    private void postDetailPesananFromApi() {
        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseDetailPembelian> call = pembelianServices.getDetailPembelian(
                authorization,
                id
        );

        call.enqueue(new Callback<ServerResponseDetailPembelian>() {
            @Override
            public void onResponse(Call<ServerResponseDetailPembelian> call, Response<ServerResponseDetailPembelian> response) {
                if (response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")) {
                    Log.d("hahe", String.valueOf(response.body().getPenumpang().size()));
                    setData(response.body());
                    setStateReady();
                    setStateTransparentLoading(false);
                } else {
                    setStateNodata();
                    Toast.makeText(ReviewKapalDetailActivity.this, response.body().getResponse_code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseDetailPembelian> call, Throwable t) {
                setStateNodata();
                Toast.makeText(ReviewKapalDetailActivity.this, "Gagal memuat transaksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //MENGIRIM DATA REVIEW KE API
    private void postReviewToApi(){
        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseModels> call = pembelianServices.postReview(
                authorization,
                id,
                (int)ratingBar.getRating(),
                etUlasanKapal.getText().toString()
        );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if (response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")) {
                    postDetailPesananFromApi();
                    ratingBar.setIsIndicator(true);
                    etUlasanKapal.setEnabled(false);
                    btnSubmit.setVisibility(View.GONE);
                    modalShowSuccess();
                } else {
                    setStateNodata();
                    modalShowError("Gagal menambahkan ulasan");
                    ReviewKapalDetailActivity.this.finish();
                    Toast.makeText(ReviewKapalDetailActivity.this, response.body().getResponse_code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                setStateNodata();
                Toast.makeText(ReviewKapalDetailActivity.this, "Gagal memuat ulasan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0
    private void setStateReady() {
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    //STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading() {
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    //STATE NO DATA SAAT DATA YANG DIBERIKAN OLEH SERVER KOSONG*/
    private void setStateNodata() {
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }

    //SHOW LOADING STATE
    private void setStateTransparentLoading(boolean status) {
        if (status) {
            this.loadingdialog = new SpotsDialog.Builder().setContext(this).setMessage("Mohon tunggu sebentar").build();
            this.loadingdialog.show();
        } else {
            if (this.loadingdialog != null && this.loadingdialog instanceof Dialog) {
                this.loadingdialog.dismiss();
            }
            this.loadingdialog = null;
        }
    }

    /*METHOD UNTUK MODAL*/
    private void modalShowSuccess(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Memberi Ulasan Berhasil")
                .setMessage("Ulasan berhasil disimpan")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("BACK", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    /*METHOD UNTUK MODAL*/
    private void modalShowError(String error){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage(error)
                .setCancelable(true)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
}