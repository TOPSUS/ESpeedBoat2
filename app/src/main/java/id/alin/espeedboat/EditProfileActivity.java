package id.alin.espeedboat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyFragment.MainActivityFragment.ProfileFragment;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.AuthServices;
import id.alin.espeedboat.MyRetrofit.Services.UserServices;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements LifecycleOwner {

    private static final int GALLERY_ADD_PROFILE = 1;
    TextView addPhoto;
    EditText  etName, etEmail, etTelepon, etAddress;
    CircleImageView circleImageView;
    Button btnUpdate, btnCancel;
    ImageButton backButton;
    String jenis_kelamin, token;

    //Progress Dialog
    ProgressDialog dialog;

    //RadioButton
    RadioGroup rgJenisKelamin;
    RadioButton rbLaki, rbPerempuan;

    /*OBJECT IMAGE*/
    private Bitmap bitmap;

    /*SHARED PREFERENCE APLIKASI*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /*PRIVATE STATIC FIELD*/
    private static final int MIN_INPUT_GENERAL = 4;
    private static final int MAX_INPUT_GENERAL = 50;
    private static final int MIN_INPUT_NOHP = 10;
    private static final int MAX_INPUT_NOHP = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String USER_IMAGE = "user_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /*DISABLE DARK MODE*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        eventListener();
    }

    //BIND LAYOUT
    private void init(){
        //PROGRES BAR INIT
        dialog = new ProgressDialog(EditProfileActivity.this);
        dialog.setCancelable(false);

        backButton = (ImageButton) findViewById(R.id.backButton);
        addPhoto = (TextView) findViewById(R.id.addPhoto);
        circleImageView = (CircleImageView) findViewById(R.id.editPhotoProfile);
        btnUpdate = (Button) findViewById(R.id.btnEditProfile);
        btnCancel = (Button) findViewById(R.id.btnCancelEditProfile);

        //TV INIT
        etName = (EditText) findViewById(R.id.etNameEditProfile);
        etEmail = (EditText) findViewById(R.id.etEmailEditProfile);
        etTelepon = (EditText) findViewById(R.id.etTeleponEditProfile);
        //dropdownjeniskelamin = (AutoCompleteTextView) findViewById(R.id.filled_exposed_dropdownEdit);
        etAddress = (EditText) findViewById(R.id.etAddressEditProfile);

        //RB INIT
        rgJenisKelamin = (RadioGroup) findViewById(R.id.rgJenisKelamin);
        rbLaki = (RadioButton) findViewById(R.id.rbLakilaki);
        rbPerempuan = (RadioButton) findViewById(R.id.rbPerempuan);

        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        MainActivity.mainActivityViewModel.getProfileLiveData().observe(this, new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData data) {
                EditProfileActivity.this.etName.setText(data.getName());
                EditProfileActivity.this.etEmail.setText(data.getEmail());
                EditProfileActivity.this.etTelepon.setText(data.getNohp());
                EditProfileActivity.this.etAddress.setText(data.getAlamat());
                token = data.getToken();

                //SET RadioButton Checked
                if(data.getJeniskelamin().equals("Laki-laki")){
                    rbLaki.setChecked(true);
                }else if(data.getJeniskelamin().equals("Perempuan")){
                    rbPerempuan.setChecked(true);
                }

                StringBuilder url = new StringBuilder(ApiClient.BASE_IMAGE_USER);
                url.append(data.getFoto());

                Glide.with(EditProfileActivity.this).load(url.toString())
                        .placeholder(R.drawable.user_placeholder)
                        .into(EditProfileActivity.this.circleImageView);

            }
        });
    }

    //ONCLICK LISTENER
    private void eventListener(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileintent = new Intent(Intent.ACTION_PICK);
                fileintent.setType("image/*");
                try {
                    startActivityForResult(fileintent,GALLERY_ADD_PROFILE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(EditProfileActivity.this, "NO STORAGE DETECTED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Radio Listener
        rgJenisKelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbChecked = (RadioButton) findViewById(checkedId);
                jenis_kelamin =  rbChecked.getText().toString();
            }
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEditProfileAPI();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK){
            Uri imgUri = data.getData();
            circleImageView.setImageURI(imgUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * METHOD YANG DIGUNAKAN UNTUK MELAKUKAN REGISTER
     * */
    private void postEditProfileAPI(){
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
        dialog.setMessage("Processing");
        dialog.show();
            UserServices services = ApiClient.getRetrofit().create(UserServices.class);
            Call<ServerResponseProfileData> call = services.editProfile(
                    data.getToken(),
                    this.etName.getText().toString().trim(),
                    this.etAddress.getText().toString().trim(),
                    this.jenis_kelamin.trim(),
                    this.etTelepon.getText().toString().trim(),
                    this.etEmail.getText().toString().trim(),
                    bitmapToString(bitmap)

            );
        Log.d("sesudah", data.getToken());

            call.enqueue(new Callback<ServerResponseProfileData>() {
                @Override
                public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                    ServerResponseProfileData newData = response.body();
                    if(response.body().getStatus().matches("success")){
                        data.setUser_id(newData.getUser_id());
                        data.setName(newData.getName());
                        data.setAlamat(newData.getAlamat());
                        data.setChat_id(newData.getChat_id());
                        data.setPin(newData.getPin());
                        data.setEmail(newData.getEmail());
                        data.setNohp(newData.getNohp());
                        data.setJeniskelamin(newData.getJeniskelamin());
                        data.setFoto(newData.getFoto());
                        data.setToken(token);
                        /*MEMBERIKAN DATA KE DALAM SHARED PREF*/
                        setSharedPreferenceData(response.body());
                        MainActivity.mainActivityViewModel.setProfileData(data);
                        dialog.dismiss();
                        modalShowEditProfileBerhasil();
                    }else{
                        dialog.dismiss();
                        modalShowError(response.body().getError().parseErrorAll());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseProfileData> call, Throwable t) {
                    dialog.dismiss();
                    modalShowError(t.getMessage());
                }
            });
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

    /*METHOD UNTUK MODAL*/
    private void modalShowEditProfileBerhasil(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("EDIT PROFILE BERHASIL")
                .setMessage("Edit Profile Telah Berhasil Dilakukan")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        EditProfileActivity.this.finish();
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

    /*MENYIMPAN KE DALAM SHARED PREFERENCES*/
    private void setSharedPreferenceData(ServerResponseProfileData data){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.USER_ID,data.getUser_id());

//        /*
//         * MEMBERIKAN PREFIX HEADER KEPADA TOKEN
//         * */
//        StringBuilder token = new StringBuilder();
//        token.append("Bearer ");
//        token.append(data.getToken());
//
//        editor.putString(Config.USER_TOKEN,token.toString());
        editor.putString(Config.USER_NAMA,data.getName());
        editor.putString(Config.USER_ALAMAT,data.getAlamat());
        editor.putString(Config.USER_CHAT_ID,data.getChat_id());
        editor.putString(Config.USER_PIN,data.getPin());
        editor.putString(Config.USER_EMAIL,data.getEmail());
        editor.putString(Config.USER_FOTO,data.getFoto());
        editor.putString(Config.USER_NOHP,data.getNohp());
        editor.putString(Config.USER_JENIS_KELAMIN,data.getJeniskelamin());

        editor.apply();
    }

    private String bitmapToString(Bitmap bitmap) {
        if(bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte [] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array, Base64.DEFAULT);
        }

        return "";

    }
}