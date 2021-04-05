package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.LupaPasswordServices;
import id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel.ObjectData.LupaPasswordData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPasswordActivity extends AppCompatActivity {

    private EditText etpass, etpassconfirm;
    private Button btnSubmit;
    private FrameLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        initWidget();
    }

    private void initWidget() {
        if(this.etpass == null){
            this.etpass = findViewById(R.id.etGantiPasswordActivityNewPass);
        }

        if(this.etpassconfirm == null){
            this.etpassconfirm = findViewById(R.id.etGantiPasswordActivityNewPassConfirm);
        }

        if(this.btnSubmit == null){
            this.btnSubmit = findViewById(R.id.btnGantiPasswordActivitySubmit);
        }

        if(this.loading == null){
            this.loading = findViewById(R.id.forgotPasswordloading);
        }

        /*MEMBERIKAN EVENT LISTENER*/
        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LupaPasswordData lupaPasswordData = ForgotPasswordActivity.lupaPasswordActivityViewModel.getLupaPasswordDataMutableLiveData().getValue();
                String email =  lupaPasswordData.getEmail();
                String code = lupaPasswordData.getKode_verifikasi_email();
                String password = etpass.getText().toString();
                String confirm_password = etpassconfirm.getText().toString();

                postNewPaswordAPI(code,email,password,confirm_password);
            }
        });
    }

    private void postNewPaswordAPI(String code, String email, String password, String confirm_password){
        setStateLoading(true);
        LupaPasswordServices services = ApiClient.getRetrofit().create(LupaPasswordServices.class);
        Call<ServerResponseModels> call = services.sendNewPassword(
                                                    code,
                                                    email,
                                                    password,
                                                    confirm_password
                                            );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    setStateLoading(false);
                    showSuccessChangePasswordModal();
                }else{
                    setStateLoading(false);
                    Toast.makeText(GantiPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                setStateLoading(false);
                Toast.makeText(GantiPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessChangePasswordModal(){
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Sukses mengganti password")
                .setMessage("Selamat password anda telah diganti")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(GantiPasswordActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(GantiPasswordActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void setStateLoading(boolean status){
        if(status){
            this.loading.setVisibility(View.VISIBLE);
        }else{
            this.loading.setVisibility(View.INVISIBLE);
        }
    }
}