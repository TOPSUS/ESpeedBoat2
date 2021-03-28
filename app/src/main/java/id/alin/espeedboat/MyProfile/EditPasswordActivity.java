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
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditPasswordActivity extends AppCompatActivity {

    Button btnUpdate, btnCancel;
    ImageButton backButton;
    EditText etOldPass, etNewPass, etConfirmPass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        /*DISABLE DARK MODE*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        eventListener();
    }

    private void init(){
        //PROGRES BAR INIT
        dialog = new ProgressDialog(EditPasswordActivity.this);
        dialog.setCancelable(false);

        backButton = (ImageButton) findViewById(R.id.backButton);
        btnUpdate = (Button) findViewById(R.id.btnEditPass);
        btnCancel = (Button) findViewById(R.id.btnCancelEditPass);

        etOldPass = (EditText) findViewById(R.id.etOldPass);
        etNewPass = (EditText) findViewById(R.id.etNewPass);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPass);
    }

    private void eventListener(){
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
                    postEditPasswordApi();
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

    private void postEditPasswordApi(){
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
        dialog.setMessage("Processing");
        dialog.show();
        UserServices services = ApiClient.getRetrofit().create(UserServices.class);
        Call<ServerResponseProfileData> call = services.editPassword(
                data.getToken(),
                this.etOldPass.getText().toString().trim(),
                this.etNewPass.getText().toString().trim()

        );

        call.enqueue(new Callback<ServerResponseProfileData>() {
            @Override
            public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                ServerResponseProfileData newData = response.body();
                if(response.body().getStatus().matches("success")){
                    dialog.dismiss();
                    modalShowEditPasswordBerhasil();
                }else if(response.body().getStatus().matches("failed")){
                    dialog.dismiss();
                    modalShowEditPasswordGagal();
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
        if(etOldPass.getText().toString().isEmpty()){
            etOldPass.setError("Masukkan Password Lama dengan Benar");
            return false;
        }
        if(etNewPass.getText().toString().isEmpty()){
            etNewPass.setError("Masukkan Password Baru");
            return false;
        }
        if(etNewPass.getText().toString().length() < 8){
            etNewPass.setError("Password Baru Minimal 8 Digit");
            return false;
        }
        if(!(etNewPass.getText().toString().equals(etConfirmPass.getText().toString()))){
            etNewPass.setError("Password Tidak Cocok");
            etConfirmPass.setError("Password Tidak Cocok");
            return false;
        }
        return true;
    }

    /*METHOD UNTUK MODAL*/
    private void modalShowEditPasswordBerhasil(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("EDIT PASSWORD BERHASIL")
                .setMessage("Edit Password Telah Berhasil Dilakukan")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        EditPasswordActivity.this.finish();
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
    private void modalShowEditPasswordGagal(){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("EDIT PASSWORD GAGAL")
                .setMessage("Password Lama Salah")
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