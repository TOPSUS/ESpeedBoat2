package id.alin.espeedboat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ProfileData;
import id.alin.espeedboat.MyRetrofit.Services.AuthServices;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RegisterActivity extends AppCompatActivity {

    /*WIDGET DI HALAMAN REGISTER*/
    private MaterialEditText metnama, metemail, metnohp, metalamat, metpassowrd, metconfirmpassord;

    private CircularProgressButton circularProgressButton;

    /*JNEIS KELAMIN*/
    private AutoCompleteTextView dropdownjeniskelamin;

    /*VARIABNLE JENIS KELAMIN*/
    private String jenis_kelamin;

    /*MINI FRAGMENT BOTTOM SHEET DIALGO*/
    private BottomSheetDialog bottomSheetDialog;

    /*BOTTOM SHEET VIEW*/
    private View bottomsheetview;

    /*
     * BOTTOM SHEET FRAGMENT KAN PUNYA WIDGET JUGA NAH ITU
     * DISINI DIA PROPERTIES NYA
     * */
    /*BOTTOM SHEET GAMBAR*/
    private CircleImageView bottomsheetcircleimage;

    /*BOTTOM SHEET TOMBOL TAMBAH GAMBAR, TOMBOL KEMBALI, TOMBOL KIRIM*/
    private Button bottomsheetpilihgambar, bottomsheetkembali;

    /*CIRCULAR BOTTOM KIRIM*/
    private CircularProgressButton bottomsheetkirim;

    /*OBJECT IMAGE*/
    private Bitmap bitmap;

    /*PRIVATE STATIC FIELD*/
    private static final int MIN_INPUT_GENERAL = 4;
    private static final int MAX_INPUT_GENERAL = 50;
    private static final int MIN_INPUT_NOHP = 10;
    private static final int MAX_INPUT_NOHP = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_IMAGE_REQUEST = 1;
    private static final int GALLERY_ADD_PROFILE = 1;
    private static final String USER_IMAGE = "user_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        /*
         * INIT SEMUA WIDGET DI LAMAN REGISTER
         * */
        initWidget();
    }

    /*
     * METHOD YANG DIGUNAKAN NTUK MELAKUYKAN INIT WIDGET
     * */
    private void initWidget() {

        /*INIT MET NAMA*/
        if (this.metnama == null) {
            this.metnama = findViewById(R.id.metRegisterActivityNama);
        }
        this.metnama.setMinCharacters(MIN_INPUT_GENERAL);
        this.metnama.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET EMAIL*/
        if (this.metemail == null) {
            this.metemail = findViewById(R.id.metRegisterActivityEmail);
        }
        this.metemail.setMinCharacters(MIN_INPUT_GENERAL);
        this.metemail.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT NO HP*/
        if (this.metnohp == null) {
            this.metnohp = findViewById(R.id.metRegisterActivityNohp);
        }
        this.metnohp.setMinCharacters(MIN_INPUT_NOHP);
        this.metnohp.setMaxCharacters(MAX_INPUT_NOHP);

        /*INIT MET ALAMAT*/
        if (this.metalamat == null) {
            this.metalamat = findViewById(R.id.metRegisterActivityAlamat);
        }
        this.metalamat.setMinCharacters(MIN_INPUT_GENERAL);
        this.metalamat.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET PASSWORD*/
        if (this.metpassowrd == null) {
            this.metpassowrd = findViewById(R.id.metRegisterActivityPassword);
        }
        this.metpassowrd.setMinCharacters(MIN_INPUT_GENERAL);
        this.metpassowrd.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET REGISTER PASSWORD*/
        if (this.metconfirmpassord == null) {
            this.metconfirmpassord = findViewById(R.id.metRegisterActivityConfirmpassword);
        }
        this.metconfirmpassord.setMinCharacters(MIN_INPUT_GENERAL);
        this.metconfirmpassord.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT JENIS KELAMIN*/
        /*MENGISI JENIS KELAMIN*/
        String[] type = new String[]{"Laki-laki", "Perempuan"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text_view_dropdown_item, type);

        /*INIT DROP DOWN*/
        if (this.dropdownjeniskelamin == null) {
            dropdownjeniskelamin = findViewById(R.id.filled_exposed_dropdown);
        }

        /*MEMASUKKAN */
        dropdownjeniskelamin.setAdapter(adapter);
        dropdownjeniskelamin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Log.d("TESST","halo");
                    RegisterActivity.this.jenis_kelamin = "Laki-laki";
                } else if (position == 1) {
                    Log.d("TESST 2","halo");
                    RegisterActivity.this.jenis_kelamin = "Perempuan";
                }
            }
        });

        /*INIT CIRCULAR BUTTON*/
        circularProgressButton = findViewById(R.id.btnRegisterActivityDaftar);


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        /*INIT MINI FRAGMENT REGISTER BOTTOM SHEET*/
        if (this.bottomSheetDialog == null) {
            bottomSheetDialog = new BottomSheetDialog(RegisterActivity.this, R.style.BottomSheetDialogTheme);
        }

        bottomsheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mini_fragment_register_activity_bottom_sheet, findViewById(R.id.minifragmentRegisterActivityBottomSheet));

        bottomSheetDialog.setContentView(bottomsheetview);

        /*INIT UNTUK GAMBAR LINGKARAN DI BOTTOM SHEET*/
        if (bottomsheetcircleimage == null) {
            bottomsheetcircleimage = bottomsheetview.findViewById(R.id.circleImageView2);
        }

        /*INIT BUTTON BOTTOM SHEET DIALOG TAMBAH GAMBAR*/
        if (this.bottomsheetpilihgambar == null) {
            this.bottomsheetpilihgambar = bottomsheetview.findViewById(R.id.btnMiniFragmentRegisterActivityTambahgambar);
        }

        this.bottomsheetpilihgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = requestRead();
                if (result) {
                    pickFile();
                }
            }
        });

        /*INIT BUTTON KIRIM*/
        bottomsheetkirim = bottomsheetview.findViewById(R.id.button);
        bottomsheetkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CHECK DULU YAKAN APAKAH DATANYA SUDAH VALIDA ATAU BELUM*/
                if(RegisterActivity.this.prepareData()){
                    bottomsheetkirim.startAnimation();
                    circularProgressButton.startAnimation();
                    postRegisterAPI();
                }else{
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }

    /*REQUEST READ FILE*/
    public boolean requestRead() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /*METHOD INI DIGUNAKAN UNTUK MEMANGGIL INTENT GALLERY / PENYIMPANAN*/
    private void pickFile() {
        Intent fileintent = new Intent(Intent.ACTION_PICK);
        fileintent.setType("image/*");
        try {
            startActivityForResult(fileintent,GALLERY_ADD_PROFILE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "NO STORAGE DETECTED", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * METHOD ACTIVITY RESULT
     * HASIL DARI PENGAMBILAN GAMBAR AKAN DIKIRIM KE SINI
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_IMAGE_REQUEST && resultCode == -1) {

            Uri imgUri = data.getData();
            bottomsheetcircleimage.setImageURI(imgUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /*
     * INI MERUPAKAN METHOD YANG DIGUNAKNA UNTUK MEMVALIDASI
     * SEMUA DATA YANG TELAH DITERIMA FORM PADA HALAMAN LOGIN
     * */
    private boolean prepareData(){
        int validation_point = 1;

        if(this.metnama.getText().toString().matches("")){
            this.metnama.setError("Mohon masukkan nama lengkap");
            validation_point -= 1;
        }

        if(this.metemail.getText().toString().matches("")){
            this.metemail.setError("Mohon masukkan E-mail anda");
            validation_point -= 1;
        }

        if(this.metnohp.getText().toString().matches("")){
            this.metnohp.setError("Mohon masukkan alamat lengkap");
            validation_point -= 1;
        }

        if(this.metalamat.getText().toString().matches("")){
            this.metalamat.setError("Mohon masukkan alamat lengkap");
        }

        if(this.metpassowrd.getText().toString().matches("")){
            this.metpassowrd.setError("Mohon Masukkan");
            validation_point -= 1;
        }

        if(this.metconfirmpassord.getText().toString().matches("")){
            this.metconfirmpassord.setError("Mohon Masukkan Confirmasi Password");
            validation_point -= 1;
        }

        if(!this.metconfirmpassord.getText().toString().matches(this.metpassowrd.getText().toString())){
            this.metconfirmpassord.setError("Confirm password dan password tidak mirip");
            validation_point -= 1;
        }

        if(this.jenis_kelamin == null){
            this.dropdownjeniskelamin.setError("Field ini perlu diisi");
            validation_point -= 1;
        }

        if(validation_point < 1){
            return false;
        }
        else{
            return true;
        }
    }
    /*
    * METHOD YANG DIGUNAKAN UNTUK MELAKUKAN REGISTER
    * */
    private void postRegisterAPI(){

        /*MENGIRIM DENGAN GAMBAR*/
        if(this.bitmap != null){
            /*MENGUBAH IMAGE BITMAP KE BENTUK FILE*/
            File imagefile =  persistImage(this.bitmap,USER_IMAGE);

            /*MEMBUAT REQUEST UNTUK MULTIPART FORM DATA*/
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageprofile", USER_IMAGE, requestFile);
            RequestBody nama = RequestBody.create(MediaType.parse("multipart/form-data"), this.metnama.getText().toString().trim());
            RequestBody alamat = RequestBody.create(MediaType.parse("multipart/form-data"), this.metalamat.getText().toString().trim());
            RequestBody jeniskelamin = RequestBody.create(MediaType.parse("multipart/form-data"), this.jenis_kelamin.trim());
            RequestBody nohp = RequestBody.create(MediaType.parse("multipart/form-data"), this.metnohp.getText().toString().trim());
            RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), this.metemail.getText().toString().trim());
            RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), this.metpassowrd.getText().toString().trim());
            RequestBody c_password = RequestBody.create(MediaType.parse("multipart/form-data"), this.metconfirmpassord.getText().toString().trim());

            AuthServices services = ApiClient.getRetrofit().create(AuthServices.class);
            Call<ProfileData> call = services.registerImage(
                    nama,
                    alamat,
                    jeniskelamin,
                    nohp,
                    email,
                    password,
                    c_password,
                    body
            );

            call.enqueue(new Callback<ProfileData>() {
                @Override
                public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                    bottomsheetkirim.revertAnimation();
                    circularProgressButton.revertAnimation();

                    if(response.body().getStatus().matches("success")){
                        modalShowRegistrasiBerhasil();
                    }else{
                        modalShowError(response.body().getError().parseErrorAll());
                    }
                }

                @Override
                public void onFailure(Call<ProfileData> call, Throwable t) {
                    bottomsheetkirim.revertAnimation();
                    circularProgressButton.revertAnimation();
                    modalShowError(t.getMessage());
                }
            });

            /*TANPA GAMBAR*/
        }else{
            AuthServices services = ApiClient.getRetrofit().create(AuthServices.class);
            Call<ProfileData> call = services.registerNoImage(
                    this.metnama.getText().toString().trim(),
                    this.metalamat.getText().toString().trim(),
                    this.jenis_kelamin.trim(),
                    this.metnohp.getText().toString().trim(),
                    this.metemail.getText().toString().trim(),
                    this.metpassowrd.getText().toString().trim(),
                    this.metconfirmpassord.getText().toString().trim()
            );

            call.enqueue(new Callback<ProfileData>() {
                @Override
                public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                    bottomsheetkirim.revertAnimation();
                    circularProgressButton.revertAnimation();

                    if(response.body().getStatus().matches("success")){
                        modalShowRegistrasiBerhasil();
                    }else{
                        modalShowError(response.body().getError().parseErrorAll());
                    }
                }

                @Override
                public void onFailure(Call<ProfileData> call, Throwable t) {
                    bottomsheetkirim.revertAnimation();
                    circularProgressButton.revertAnimation();
                    modalShowError(t.getMessage());
                }
            });
        }

    }

    /*METHOD UNTUK MODAL*/
    private void modalShowRegistrasiBerhasil(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("REGISTRASI BERHASIL")
                .setMessage("Silahkan melakukan verifikasi email, dan login kembali")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        RegisterActivity.this.finish();
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

    /*BITMAP TO FILE*/
    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = this.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error writing bitmap", e);
        }

        return imageFile;
    }
}