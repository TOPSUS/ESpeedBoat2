package id.alin.espeedboat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import dmax.dialog.SpotsDialog;
import id.alin.espeedboat.MyAdapter.PenumpangDetailAdapter;
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
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private long timeLeft = 600000;

    private NestedScrollView detail;
    private LinearLayout loading, nodata, layoutBelumDibayar, layoutMenungguKonfirmasi, layoutTerkonfirmasi;
    CountDownTimer countDownTimer;
    TextView tvStatusBayar, tvCountDown, tvKapal, tvTanggal, tvHarga, tvAsal, tvTujuan, tvBerangkat, tvSampai, tvNamaPemesan, tvEmailPemesan, tvTeleponPemesan;
    Button btnUploadBukti, btnBatalPesan, btndownloadticket;
    ImageButton btnBack;
    RecyclerView recyclerView;
    PenumpangDetailAdapter penumpangDetailAdapter;

    //*BOTTOM SHEET UPLOAD BUKTI*/
    private BottomSheetDialog bottomSheetDialog;
    private View bottomsheetview;
    private File file_image_bukti;

    //ATRIBUTE STATIC PROSES PEMBAYARAN
    private static final String IMAGE_PEMBAYARAN = "IMAGE_PEMBAYARAN";
    private static final String IMAGE_PEMBAYARAN_FILE_NAME = "IMAGE_PEMBAYARAN_FILE_NAME.jpg";
    private Dialog loadingdialog;

    /*ATRIBUTE KAMERA UNTUK NGAMBIL BUKTI PEMBAYARAN*/
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 2;

    /*ATRIBUTE STORAGE UNTUK NGAMBIL BUKTI PEMBAYARAN*/
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int GALLERY_ADD_PROFILE = 1;

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

        this.btnUploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialogUploadBukti(true);
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
                    setStateTransparentLoading(false);
                    showBottomDialogUploadBukti(false);
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
    private void postBuktiPembayaranToApi(File bukti_pembayaran, String token, String id_user){
        //JALANKAN LOADING TRANSPARENT STATE
        setStateTransparentLoading(true);

        //MEMBUAT SEBUAH REQUEST BODY UNTUK MULTIPART
        RequestBody requestFile = RequestBody.create(bukti_pembayaran,MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image_bukti_pembayaran",IMAGE_PEMBAYARAN, requestFile);

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization",MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken());

        RequestBody id_pembelian = RequestBody.create(String.valueOf(id),(MediaType.parse("multipart/form-data")));

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

    //DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0
    private void setStateReady(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.VISIBLE);
    }

    //STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading(){
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
        this.detail.setVisibility(View.INVISIBLE);
    }

    //STATE NO DATA SAAT DATA YANG DIBERIKAN OLEH SERVER KOSONG*/
    private void setStateNodata(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }

    //SHOW LOADING STATE
    private void setStateTransparentLoading(boolean status){
        if(status){
            this.loadingdialog = new SpotsDialog.Builder().setContext(this).setMessage("Mohon tunggu sebentar").build();
            this.loadingdialog.show();
        }else{
            if(this.loadingdialog != null && this.loadingdialog instanceof Dialog){
                this.loadingdialog.dismiss();
            }
            this.loadingdialog = null;
        }
    }

    //*COUNT DOWN METHOD*/
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

    //*UPDATE METHOD UNTUK COUNTDOWN*/
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

    //METHOD UNTUK MEMPERLIHATKAN BOTTOMSHEETDIALOG
    private void showBottomDialogUploadBukti(boolean status){
        /*APABILA TRUE MAKA BUKA DIALOG*/
        if(status){
            bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogTheme);
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
                    if(file_image_bukti.exists()){
                        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

                        postBuktiPembayaranToApi(file_image_bukti,profileData.getToken(),profileData.getUser_id());

                    }else{
                        Toast.makeText(MyUnpaidDetailTransactionActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    file_image_bukti = null;
                }
            });

            bottomSheetDialog.show();
        }else{
            if(this.bottomSheetDialog != null){
                if(this.bottomSheetDialog.isShowing()){
                    this.bottomSheetDialog.dismiss();
                    this.bottomSheetDialog = null;
                }
            }
        }
    }

    //METHOD UNTUK MEMBUKA KAMERA UPLOAD BUKTI PEMBAYARAN
    private void openCamera(){
        /*CEK IZIN TERLEBIH DAHULU*/
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    //METHOD UNTUK MEMBUKA STORAGE DAN MENGAMBIL BUKTI PEMBAYARAN*/
    private void pickFile() {
        if(requestRead()){
            Intent fileintent = new Intent(Intent.ACTION_PICK);
            fileintent.setType("image/*");
            try {
                startActivityForResult(fileintent,GALLERY_ADD_PROFILE);
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
        if(requestCode == CAMERA_REQUEST && resultCode == SUCCES_RESULT_CODE){
            /*CHECK BOTTOM SHEET VIEW AKTIF ATAU TIDAK*/
            if(this.bottomsheetview != null && this.bottomsheetview.isShown()){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                ImageView imageView = this.bottomsheetview.findViewById(R.id.invoicebuktiimage);
                imageView.setImageBitmap(bitmap);

                this.file_image_bukti = convertBitmapToFile(bitmap);
            }else{
                Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        }

        /*STORAGE*/
        if(requestCode == GALLERY_ADD_PROFILE && resultCode == SUCCES_RESULT_CODE){
            Uri imgUri = data.getData();

            /*CHECK BOTTOM SHEET VIEW AKTIF ATAU TIDAK*/
            if(this.bottomsheetview != null && this.bottomsheetview.isShown()){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);

                    ImageView imageView = this.bottomsheetview.findViewById(R.id.invoicebuktiimage);
                    imageView.setImageBitmap(bitmap);

                    this.file_image_bukti = convertBitmapToFile(bitmap);

                } catch (Exception e) {
                    Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //BITMAP TO FILE CONVERTER METHOD
    private File convertBitmapToFile(Bitmap bitmap){
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
            fos = new FileOutputStream(f,false);
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

}