package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import dmax.dialog.SpotsDialog;
import id.alin.espeedboat.MyAdapter.PenumpangDetailAdapter;
import id.alin.espeedboat.MyFragment.MainActivityFragment.ProfileFragment;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian.ServerResponseDetailPembelian;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import id.alin.espeedboat.MyRoom.Entity.PenumpangDetailEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUnpaidDetailTransactionActivity extends AppCompatActivity {
    private long id = 0;
    private int position;
    private long timeLeft;
    private static final int PERMISSION_STORAGE_CODE = 1000;

    private NestedScrollView detail;
    private LinearLayout loading, nodata, layoutBelumDibayar, layoutMenungguKonfirmasi, layoutTerkonfirmasi, layoutPembayaran, layoutCardPembayaran, layoutKendaraan;
    CountDownTimer countDownTimer;
    ImageView ivMetode;
    TextView tvStatusBayar, tvCountDown, tvKapal, tvTanggal, tvHarga, tvAsal, tvTujuan, tvBerangkat, tvSampai, tvNamaPemesan, tvEmailPemesan, tvTeleponPemesan, tvMetode, tvPenjelasanMetode, tvRekening, tvLayoutPembayaran, tvCardTindakan, tvKendaraan, tvGolongan, tvNopol, tvHargaGolongan;
    Button btnUploadBukti, btnBatalPesan, btndownloadticket, btnuploadulangbukti, btnExtend, btnDownloadBukti;
    ImageButton btnBack;
    RecyclerView recyclerView;
    PenumpangDetailAdapter penumpangDetailAdapter;
    CardView cvTindakan;

    //*BOTTOM SHEET UPLOAD BUKTI*/
    private BottomSheetDialog bottomSheetDialog;
    private View bottomsheetview;
    private File file_image_bukti;

    //ATRIBUTE STATIC PROSES PEMBAYARAN
    private static final String IMAGE_PEMBAYARAN = "IMAGE_PEMBAYARAN";
    private static final String IMAGE_PEMBAYARAN_FILE_NAME = "IMAGE_PEMBAYARAN_FILE_NAME.jpg";
    private Dialog loadingdialog;
    private MaterialDialog mDialog;

    //ATRIBUTE KAMERA UNTUK NGAMBIL BUKTI PEMBAYARAN*/
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 2;

    /*ATRIBUTE STORAGE UNTUK NGAMBIL BUKTI PEMBAYARAN*/
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int GALLERY_ADD_PROFILE = 1;

    /*URL DOWNLOAD BUKTI*/
    private String downloadUrl = ApiClient.BASE_FILE_BUKTI_PEMBAYARAN;
    private String namaBukti;

    // STATUS PEMBELIAN
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_unpaid_detail_transaction);
        init();
        setStateLoading();
    }

    private void init() {
        if (getIntent().hasExtra("id_trans")) {
            id = getIntent().getLongExtra("id_trans", 3);
            position = getIntent().getIntExtra("position", 0);
            Log.d("jajaja", String.valueOf(id));

        }
        //init metode pembayaran
        ivMetode = (ImageView) findViewById(R.id.ivLogoPembayaran);
        tvMetode = (TextView) findViewById(R.id.tvNamaMetode);
        tvPenjelasanMetode = (TextView) findViewById(R.id.tvpenjelasanmetode);
        tvRekening = (TextView) findViewById(R.id.rekening);
        tvLayoutPembayaran = (TextView) findViewById(R.id.tvLayoutPembayaran);
        tvCardTindakan = (TextView) findViewById(R.id.tvTindakan);

        //init kendaraan
        tvKendaraan = (TextView) findViewById(R.id.tvKendaraan);
        layoutKendaraan = (LinearLayout) findViewById(R.id.layoutKendaraan);
        tvGolongan = (TextView) findViewById(R.id.golongan);
        tvNopol = (TextView) findViewById(R.id.nopol);
        tvHargaGolongan = (TextView) findViewById(R.id.harga_golongan);

        btnBack = (ImageButton) findViewById(R.id.backButton);
        btnExtend = (Button) findViewById(R.id.btnExtendPembayaran);
        detail = (NestedScrollView) findViewById(R.id.nestedDetail);
        loading = (LinearLayout) findViewById(R.id.loadinglayout);
        nodata = (LinearLayout) findViewById(R.id.nodatalayout);
        layoutBelumDibayar = (LinearLayout) findViewById(R.id.layoutBelumDibayar);
        layoutMenungguKonfirmasi = (LinearLayout) findViewById(R.id.layoutMenungguKonfirmasi);
        layoutTerkonfirmasi = (LinearLayout) findViewById(R.id.layoutTerkonfirmasi);
        layoutPembayaran = (LinearLayout) findViewById(R.id.lldetailPembayaran);
        layoutCardPembayaran = (LinearLayout) findViewById(R.id.layoutCardPembayaran);
        cvTindakan = (CardView) findViewById(R.id.cardViewTindakan);


        recyclerView = (RecyclerView) findViewById(R.id.rvPenumpang);
        btnUploadBukti = (Button) findViewById(R.id.btnUploadBukti);
        btnBatalPesan = (Button) findViewById(R.id.btnBatalPesan);
        btnuploadulangbukti = findViewById(R.id.btnUploadUlangBukti);
        btnDownloadBukti = (Button) findViewById(R.id.btnDownloadBukti);
        this.btndownloadticket = findViewById(R.id.btnDownloadTiket);

        this.btndownloadticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PdfViewerActivity.Companion.launchPdfFromUrl(
                        MyUnpaidDetailTransactionActivity.this,
                        "https://www.espeedboat.xyz/storage/test_pdf/ticket_pdf.pdf",
                        "E-Ticket",
                        "",
                        true
                ));
            }
        });

        this.btnDownloadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBuktiPembayaranFromApi();
            }
        });

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

        btnExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPembayaran.getVisibility() == View.GONE) {
                    layoutPembayaran.setVisibility(View.VISIBLE);
                    btnExtend.setBackgroundResource(R.drawable.ic_arrowup);
                } else if (layoutPembayaran.getVisibility() == View.VISIBLE) {
                    layoutPembayaran.setVisibility(View.GONE);
                    btnExtend.setBackgroundResource(R.drawable.ic_arrow_down);
                }

            }
        });

        // BTN UPLOAD BUKTI LISTENER
        this.btnUploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialogUploadBukti(true);
            }
        });

        //BTN UPLOAD ULANG BUKTI
        this.btnuploadulangbukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialogUploadBukti(true);
            }
        });

        // BTN BATALKAN PEMBELIAN
        this.btnBatalPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModalTanyaUntukPembatalan();
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

        if (body.getGolongan().equals("NOPE")) {
            tvKendaraan.setVisibility(View.GONE);
            layoutKendaraan.setVisibility(View.GONE);
        } else {
            String harga_golongan = "IDR " + kursIndonesia.format(body.getHarga_golongan());
            tvGolongan.setText(body.getGolongan());
            tvNopol.setText("Nomor Polisi: " + body.getNomor_polisi());
            tvHargaGolongan.setText(harga_golongan);
        }

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

        tvNamaPemesan.setText(body.getNama_pemesan());
        tvEmailPemesan.setText("Email: " + body.getEmail_pemesan());
        tvTeleponPemesan.setText("Telepon: " + body.getTelepon_pemesan());

        //ivMetode = (ImageView) findViewById(R.id.ivLogoPembayaran);
        tvMetode.setText(body.getMetode_pembayaran());
        tvPenjelasanMetode.setText("Lakukan pembayaran dengan transfer ke nomor rekening berikut");
        tvRekening.setText("Nomor Rekening: " + body.getRekening());
        downloadUrl = downloadUrl + body.getBukti();
        namaBukti = body.getBukti();


        timeLeft = body.getSisa_waktu();
        ;
        countDown();

        StringBuilder url = new StringBuilder(ApiClient.BASE_LOGO_METODE_PEMBAYARAN);
        url.append(body.getLogo_metode());

        Glide.with(MyUnpaidDetailTransactionActivity.this).load(url.toString())
                .placeholder(R.drawable.wallet)
                .into(MyUnpaidDetailTransactionActivity.this.ivMetode);

        //SWITCH LAYOUT BY STATUS
        if (body.getStatus_transaksi().equals("menunggu pembayaran")) {
            tvStatusBayar.setText("Belum Dibayar");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Danger_Red));
            layoutMenungguKonfirmasi.setVisibility(View.GONE);
            layoutBelumDibayar.setVisibility(View.VISIBLE);
            layoutTerkonfirmasi.setVisibility(View.GONE);
            layoutCardPembayaran.setVisibility(View.VISIBLE);
            tvLayoutPembayaran.setVisibility(View.VISIBLE);
        } else if (body.getStatus_transaksi().equals("menunggu konfirmasi")) {
            tvStatusBayar.setText("Menunggu Konfirmasi");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Warning_Orange));
            tvCountDown.setVisibility(View.GONE);
            layoutMenungguKonfirmasi.setVisibility(View.VISIBLE);
            layoutBelumDibayar.setVisibility(View.GONE);
            layoutTerkonfirmasi.setVisibility(View.GONE);
            layoutCardPembayaran.setVisibility(View.GONE);
            tvLayoutPembayaran.setVisibility(View.GONE);
        } else if (body.getStatus_transaksi().equals("terkonfirmasi")) {
            tvStatusBayar.setText("Dibayar");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Safety_Green));
            tvCountDown.setVisibility(View.GONE);
            layoutMenungguKonfirmasi.setVisibility(View.GONE);
            layoutBelumDibayar.setVisibility(View.GONE);
            layoutTerkonfirmasi.setVisibility(View.VISIBLE);
            layoutCardPembayaran.setVisibility(View.GONE);
            tvLayoutPembayaran.setVisibility(View.GONE);
        } else if (body.getStatus_transaksi().equals("dibatalkan")) {
            tvStatusBayar.setText("Dibatalkan");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Danger_Red));
            tvCountDown.setVisibility(View.GONE);
            layoutTerkonfirmasi.setVisibility(View.GONE);
            layoutCardPembayaran.setVisibility(View.GONE);
            tvLayoutPembayaran.setVisibility(View.GONE);
            tvCardTindakan.setVisibility(View.GONE);
            cvTindakan.setVisibility(View.GONE);
        } else if (body.getStatus_transaksi().equals("expired")) {
            tvStatusBayar.setText("Kadaluwarsa");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Danger_Red));
            tvCountDown.setVisibility(View.GONE);
            tvCardTindakan.setVisibility(View.GONE);
            cvTindakan.setVisibility(View.GONE);
            layoutCardPembayaran.setVisibility(View.GONE);
            tvLayoutPembayaran.setVisibility(View.GONE);
            layoutMenungguKonfirmasi.setVisibility(View.GONE);
        } else if (body.getStatus_transaksi().equals("digunakan")) {
            tvStatusBayar.setText("Selesai");
            tvStatusBayar.setTextColor(ContextCompat.getColor(MyUnpaidDetailTransactionActivity.this, R.color.Safety_Green));
            tvCountDown.setVisibility(View.GONE);
            layoutCardPembayaran.setVisibility(View.GONE);
            tvLayoutPembayaran.setVisibility(View.GONE);
            btnuploadulangbukti.setVisibility(View.GONE);
            btnBatalPesan.setVisibility(View.GONE);
        }
    }

    //KELOLA PEMBAYARAB

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
                    setRecyclerView(response.body().getPenumpang());
                    setData(response.body());
                    setStateReady();
                    setStateTransparentLoading(false);
                    showBottomDialogUploadBukti(false);
                } else {
                    setStateNodata();
                    Toast.makeText(MyUnpaidDetailTransactionActivity.this, response.body().getResponse_code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseDetailPembelian> call, Throwable t) {
                setStateNodata();
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, "Gagal memuat transaksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //UPLOAD BUKTI PEMBAYARAN
    private void postBuktiPembayaranToApi(File bukti_pembayaran, String token, String id_user) {
        //JALANKAN LOADING TRANSPARENT STATE
        setStateTransparentLoading(true);

        //MEMBUAT SEBUAH REQUEST BODY UNTUK MULTIPART
        RequestBody requestFile = RequestBody.create(bukti_pembayaran, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image_bukti_pembayaran", IMAGE_PEMBAYARAN, requestFile);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken());

        RequestBody id_pembelian = RequestBody.create(String.valueOf(id), (MediaType.parse("multipart/form-data")));

        //MEMANGGIL LIBRARY RETROFIT UNTUK POST BUKTI PEMBAYARAN
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseModels> call = pembelianServices.postBuktiPembayaran(
                headers,
                body,
                id_pembelian
        );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                postDetailPesananFromApi();
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                postDetailPesananFromApi();
            }
        });
    }

    // BATALKAN PEMBELIAN API
    private void postBatalkanPembelianAPI(String token) {
        //HIDUPKAN TRANSPARENT LOADING STATE
        setStateTransparentLoading(true);

        // PANGGIL SERVICE RETROFIT
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseModels> call = pembelianServices.postBatalkanPembelian(
                token,
                String.valueOf(id)
        );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if (response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")) {
                    setStateTransparentLoading(false);
                    setStateLoading();
                    postDetailPesananFromApi();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                setStateTransparentLoading(false);
            }
        });
    }

    //BATALKAN PESANAN
    private void postBatalPesanToApi() {

    }

    //DOWNLOAD BUKTI PEMBAYARAN
    private void postBuktiPembayaranFromApi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //if denied, grant it
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //popup
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            } else {
                startDownload();
            }
        } else {

        }
        Toast.makeText(getApplicationContext(), "Downloading", Toast.LENGTH_SHORT).show();
    }

    private void startDownload() {
        //download req from url
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        //ijin koneksi wifi dan data
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        request.setTitle("Download");
//        request.setDescription("Downloading Journal...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, System.currentTimeMillis() + namaBukti); //get datetime untuk nama file nantinya

        //download service
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //DOWNLOAD TIKET
    private void postTiketFromApi() {

    }

    //SET RECYLERVIEW
    private void setRecyclerView(List<PenumpangDetailEntity> penumpangDetailEntities) {
        penumpangDetailAdapter = new PenumpangDetailAdapter(MyUnpaidDetailTransactionActivity.this, penumpangDetailEntities);
        recyclerView.setAdapter(penumpangDetailAdapter);
    }

    //DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0
    private void setStateReady() {
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.VISIBLE);
    }

    //STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading() {
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.INVISIBLE);
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

    //*COUNT DOWN METHOD*/
    private void countDown() {
        countDownTimer = new CountDownTimer(timeLeft, 10) {
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

    //*UPDATE METHOD UNTUK COUNTDOWN*/
    private void updateTimer() {
        int menit = (int) timeLeft / 60000;
        int detik = (int) timeLeft % 60000 / 1000;

        String timeLeft;
        timeLeft = "Batas Waktu: " + menit + " mnt ";
        timeLeft = timeLeft + detik + " dtk";
        if (menit == 0 && detik == 0) {
            countDownTimer.cancel();
            tvCountDown.setVisibility(View.GONE);
            tvStatusBayar.setText("Kadaluwarsa");
            btnUploadBukti.setVisibility(View.GONE);
            btnBatalPesan.setVisibility(View.GONE);
            status = "expired";
            postPembelianStatus(status);
            MyUnpaidTransactionActivity.recyclerView.getAdapter().notifyItemRemoved(position);
            MyUnpaidTransactionActivity.recyclerView.getAdapter().notifyDataSetChanged();
            Log.d("haheh", String.valueOf(position));
        } else {
            tvCountDown.setText(timeLeft);
        }
    }

    private void postPembelianStatus(String status) {
        Log.d("testes", String.valueOf(id));
        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
        setStateTransparentLoading(true);
        PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
        Call<ServerResponseModels> call = pembelianServices.postPembelianStatus(
                authorization,
                id,
                status
        );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                postDetailPesananFromApi();
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(MyUnpaidDetailTransactionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                postDetailPesananFromApi();
            }
        });
    }

    //METHOD UNTUK MEMPERLIHATKAN BOTTOMSHEETDIALOG
    private void showBottomDialogUploadBukti(boolean status) {
        /*APABILA TRUE MAKA BUKA DIALOG*/
        if (status) {
            if (bottomSheetDialog == null) {
                bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
                bottomsheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_ambil_bukti_pembayaran, findViewById(R.id.bottom_sheet_ambil_bukti_pembayaran));
                bottomSheetDialog.setContentView(bottomsheetview);

                //MENGATUR BTN CAMERA LIESTENER
                Button btn_camere = bottomsheetview.findViewById(R.id.btnMiniFragmentRegisterActivityTambahgambar);
                btn_camere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                    }
                });

                //MENGATUR BTN STORAGE LIESTENER
                Button btn_storage = bottomsheetview.findViewById(R.id.storage);
                btn_storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickFile();
                    }
                });

                //MENGATUR BTN KIRIM LIESTENER
                Button btn_kirim = bottomsheetview.findViewById(R.id.buttonkirim);
                btn_kirim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (file_image_bukti != null) {
                            ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

                            postBuktiPembayaranToApi(file_image_bukti, profileData.getToken(), profileData.getUser_id());

                        } else {
                            Toast.makeText(MyUnpaidDetailTransactionActivity.this, "Pilih bukti terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        file_image_bukti = null;
                        bottomSheetDialog = null;
                    }
                });

                bottomSheetDialog.show();
            }
        }
    }

    //METHOD UNTUK MEMBUKA KAMERA UPLOAD BUKTI PEMBAYARAN
    private void openCamera() {
        /*CEK IZIN TERLEBIH DAHULU*/
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    //METHOD UNTUK MEMBUKA STORAGE DAN MENGAMBIL BUKTI PEMBAYARAN*/
    private void pickFile() {
        if (requestRead()) {
            Intent fileintent = new Intent(Intent.ACTION_PICK);
            fileintent.setType("image/*");
            try {
                startActivityForResult(fileintent, GALLERY_ADD_PROFILE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "NO STORAGE DETECTED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //REQUEST READ FILE*/
    public boolean requestRead() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MyUnpaidDetailTransactionActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final int SUCCES_RESULT_CODE = -1;

        super.onActivityResult(requestCode, resultCode, data);

        /*CAMERA*/
        if (requestCode == CAMERA_REQUEST && resultCode == SUCCES_RESULT_CODE) {
            /*CHECK BOTTOM SHEET VIEW AKTIF ATAU TIDAK*/
            if (this.bottomsheetview != null && this.bottomsheetview.isShown()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                ImageView imageView = this.bottomsheetview.findViewById(R.id.invoicebuktiimage);
                imageView.setImageBitmap(bitmap);

                this.file_image_bukti = convertBitmapToFile(bitmap);
            } else {
                Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        }

        /*STORAGE*/
        if (requestCode == GALLERY_ADD_PROFILE && resultCode == SUCCES_RESULT_CODE) {
            Uri imgUri = data.getData();

            /*CHECK BOTTOM SHEET VIEW AKTIF ATAU TIDAK*/
            if (this.bottomsheetview != null && this.bottomsheetview.isShown()) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);

                    ImageView imageView = this.bottomsheetview.findViewById(R.id.invoicebuktiimage);
                    imageView.setImageBitmap(bitmap);

                    this.file_image_bukti = convertBitmapToFile(bitmap);

                } catch (Exception e) {
                    Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //BITMAP TO FILE CONVERTER METHOD
    private File convertBitmapToFile(Bitmap bitmap) {
        //create a file to write bitmap data
        File f = new File(getCacheDir(), IMAGE_PEMBAYARAN_FILE_NAME);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    // MEMPERLIHATKAN PEMBATALAN PEMESANAN DIALOG
    private void showModalTanyaUntukPembatalan() {

        // MATERIAL DIALOG MODAL DIALOG
        if(this.mDialog == null) {
            mDialog = new MaterialDialog.Builder(this)
                    .setTitle("BATALKAN PEMBELIAN ?")
                    .setMessage("Aksi ini tidak akan dapat di kembalikan")
                    .setCancelable(false)
                    .setAnimation(R.raw.animation_boat_2)
                    .setPositiveButton("BATALKAN", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            String token = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
                            postBatalkanPembelianAPI(token);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("KEMBALI", new AbstractDialog.OnClickListener() {
                        @Override
                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();

            mDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface) {
                    mDialog = null;
                }
            });
            // Show Dialog
            mDialog.show();
        }
    }
}