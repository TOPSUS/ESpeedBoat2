package id.alin.espeedboat.MyProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.UserServices;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddPinActivity extends AppCompatActivity {

    Button btnUpdate, btnCancel;
    ImageButton backButton;
    EditText etNewPin, etConfirmPin, etPassword;
    ProgressDialog dialog;

    /*SHARED PREFERENCE APLIKASI*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pin);

        /*DISABLE DARK MODE*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        eventListener();
    }

    private void init(){
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        //PROGRES BAR INIT
        dialog = new ProgressDialog(AddPinActivity.this);
        dialog.setCancelable(false);

        backButton = (ImageButton) findViewById(R.id.backButton);
        btnUpdate = (Button) findViewById(R.id.btnAddPin);
        btnCancel = (Button) findViewById(R.id.btnCancelAddPin);

        etNewPin = (EditText) findViewById(R.id.etNewAddPin);
        etConfirmPin = (EditText) findViewById(R.id.etConfirmAddPin);
        etPassword = (EditText) findViewById(R.id.etPasswordAddPin);
    }

    private void eventListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    postAddPinApi();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void postAddPinApi(){
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
        dialog.setMessage("Processing");
        dialog.show();
        UserServices services = ApiClient.getRetrofit().create(UserServices.class);
        Call<ServerResponseProfileData> call = services.addPin(
                data.getToken(),
                this.etNewPin.getText().toString().trim(),
                this.etPassword.getText().toString().trim()

        );

        call.enqueue(new Callback<ServerResponseProfileData>() {
            @Override
            public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                ServerResponseProfileData newData = response.body();
                if(response.body().getStatus().matches("success")){
                    data.setPin(newData.getPin());
                    setSharedPreferenceData(response.body());
                    MainActivity.mainActivityViewModel.setProfileData(data);
                    dialog.dismiss();
                    modalShowAddPinBerhasil();
                }else if(response.body().getStatus().matches("failed")){
                    dialog.dismiss();
                    modalShowAddPinGagal();
                }else{
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

    private boolean validate(){
        if(etNewPin.getText().toString().length() !=6){
            etNewPin.setError("Masukkan PIN 6 Digit!");
            return false;
        }
        if(!(etConfirmPin.getText().toString().equals(etNewPin.getText().toString()))){
            etConfirmPin.setError("PIN Tidak Cocok");
            etNewPin.setError("PIN Tidak Cocok");
            return false;
        }
        if(etPassword.getText().toString().isEmpty()){
            etPassword.setError("Masukkan Password Anda");
            return false;
        }
        return true;
    }

    /*METHOD UNTUK MODAL*/
    private void modalShowAddPinBerhasil(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("PENAMBAHAN PIN BERHASIL")
                .setMessage("Tambah Pin Telah Berhasil Dilakukan")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        AddPinActivity.this.finish();
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
    private void modalShowAddPinGagal(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("PENAMBAHAN PIN GAGAL")
                .setMessage("Password Salah")
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
}