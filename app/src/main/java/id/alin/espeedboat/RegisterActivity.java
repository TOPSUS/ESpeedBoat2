package id.alin.espeedboat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import retrofit2.http.Part;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.util.regex.Pattern;

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
    private File imageFile;

    /*PRIVATE STATIC FIELD*/
    private static final int MIN_INPUT_GENERAL = 4;
    private static final int MAX_INPUT_GENERAL = 50;
    private static final int MIN_INPUT_NOHP = 10;
    private static final int MAX_INPUT_NOHP = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                    Toast.makeText(RegisterActivity.this, "HAI", Toast.LENGTH_SHORT).show();
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
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("image/*");
        try {
            startActivityForResult(fileintent, 1);
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

            Log.d("result", String.valueOf(resultCode));
            Uri selectedImage = data.getData();

            String result = getPathFromURI(selectedImage);
            imageFile = new File(result);

            if (imageFile.exists()) {
                this.bottomsheetcircleimage.setImageURI(selectedImage);
                bottomsheetkirim.setText("KIRIM");
            }
            else {
                result = selectedImage.getPath();
                imageFile = new File(result);
                if(imageFile.exists()){
                    this.bottomsheetcircleimage.setImageURI(selectedImage);
                    bottomsheetkirim.setText("KIRIM");
                }else{
                    Toast.makeText(this, "TIDAK DAPAT MENGGUNAKAN GAMBAR TERSEBUT !", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /*
     * INI UNTUK MENDAPATKAN PATH ASLI DARI URI YANG DIBERIKAN OLEH INTENT DATA
     * */
    @SuppressLint("ObsoleteSdkInt")
    private String getPathFromURI(Uri uri) {
        String realPath = "";
        // SDK < API11
        if (Build.VERSION.SDK_INT < 11) {
            String[] proj = {MediaStore.Images.Media.DATA};
            @SuppressLint("Recycle") Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            int column_index = 0;
            String result = "";
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPath = cursor.getString(column_index);
            }
        }
        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19) {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(this.getApplicationContext(), uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                realPath = cursor.getString(column_index);
            }
        }
        // SDK > 19 (Android 4.4)
        else {
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            Log.d("colon", wholeID);
            String id = wholeID;
            if (wholeID.contains(":")) {
                id = wholeID.split(":")[1];
            }
            String[] column = {MediaStore.Images.Media.DATA};
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
            int columnIndex = 0;
            if (cursor != null) {
                columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    realPath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        }
        return realPath;
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


    private void postRegisterAPI(){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

        MultipartBody.Part body = MultipartBody.Part.createFormData("imageprofile", imageFile.getName(), requestFile);

        RequestBody nama = RequestBody.create(MediaType.parse("multipart/form-data"), this.metnama.getText().toString().trim());

        RequestBody alamat = RequestBody.create(MediaType.parse("multipart/form-data"), this.metalamat.getText().toString().trim());

        RequestBody jeniskelamin = RequestBody.create(MediaType.parse("multipart/form-data"), this.jenis_kelamin.trim());

        RequestBody nohp = RequestBody.create(MediaType.parse("multipart/form-data"), this.metnohp.getText().toString().trim());

        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), this.metemail.getText().toString().trim());

        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), this.metpassowrd.getText().toString().trim());

        RequestBody c_password = RequestBody.create(MediaType.parse("multipart/form-data"), this.metconfirmpassord.getText().toString().trim());

        AuthServices services = ApiClient.getRetrofit().create(AuthServices.class);

        Call<ProfileData> call = services.register(
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

    }

    /*METHOD UNTUK MODAL*/
    private void modalShowRegistrasiBerhasil(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("REGISTRASI BERHASIL")
                .setMessage("Silahkan melakukan verifikasi email, dan login kembali")
                .setCancelable(false)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        RegisterActivity.this.finish();
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