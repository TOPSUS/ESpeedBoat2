package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LupaPasswordVerifikasiEmailActivity extends AppCompatActivity {
    private EditText etCode;
    private Button btnMasukkanCode;
    private TextView tvKirimPasswordLagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_verifikasi_email);

        initWidget();
    }

    /*INIT SEMUA WIDGET HALAMAN*/
    private void initWidget() {
        if(this.etCode == null){
            this.etCode = findViewById(R.id.etLupaPasswordVerifikasiEmailCode);
        }

        if(this.tvKirimPasswordLagi == null){
            this.tvKirimPasswordLagi = findViewById(R.id.tvLupaPasswordVerifikasiEmailKirimUlang);
        }

        if(this.btnMasukkanCode == null){
            this.btnMasukkanCode = findViewById(R.id.btnLupaPasswordVerifikasiEmailMasukkanCode);
        }

        /*INIT LISTENER BUTTON*/
        this.btnMasukkanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidasiData()){
                    String code = LupaPasswordVerifikasiEmailActivity.this.etCode.getText().toString();

                    /*DIAMBIL DARI VIEW MODEL*/
                    String email = ForgotPasswordActivity.lupaPasswordActivityViewModel.getLupaPasswordDataMutableLiveData().getValue().getEmail();

                    /*POST CODE MENGGUNAKAN EMAIL DARI VIEW MODEL DAN CODE DARI LAMAN INI*/
                    postVerifikasiLupaPasswordCodeAPI(code,email);

                }
            }
        });

        /*INIT TEXT VIEW*/
        this.tvKirimPasswordLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ForgotPasswordActivity.lupaPasswordActivityViewModel.getLupaPasswordDataMutableLiveData().getValue().getEmail();
                sendVerifyEmailAPI(email);
                showSendEmailAgainDialog(email);
            }
        });
    }

    /*
     * MELAKUKAN PEMANGGILAN API KIRIM VERIFY EMAIL
     * */
    private void postVerifikasiLupaPasswordCodeAPI(String code, String email) {
        LupaPasswordServices services = ApiClient.getRetrofit().create(LupaPasswordServices.class);
        Call<ServerResponseModels> call = services.sendCode(
          code,
          email
        );

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if(response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")){
                    LupaPasswordData lupaPasswordData = ForgotPasswordActivity.lupaPasswordActivityViewModel.getLupaPasswordDataMutableLiveData().getValue();
                    lupaPasswordData.setKode_verifikasi_email(code);
                    ForgotPasswordActivity.lupaPasswordActivityViewModel.setLupaPasswordDataMutableLiveData(lupaPasswordData);

                    showSuccessDialog();
                }else{
                    Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    *
    * METHOD INI DIGUNAKAN UNTK MELAKUKAN PENGIRIMAN EMAIL
    * */
    private void sendVerifyEmailAPI(String email){
        LupaPasswordServices services = ApiClient.getRetrofit().create(LupaPasswordServices.class);
        Call<ServerResponseModels> call = services.sendEmail(email);

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, "EMAIL TELAH DIKIRIM", Toast.LENGTH_SHORT).show();
                }else if(response.body().getResponse_code().matches("403")){
                    Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(LupaPasswordVerifikasiEmailActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidasiData() {
        if(this.etCode.getText().toString().matches("")){
            this.etCode.setError("");

            return false;
        }else{
            return true;
        }
    }

    private void showSuccessDialog(){
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Code Cocok")
                .setMessage("Klik oke untuk mulai mengubah password anda")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(LupaPasswordVerifikasiEmailActivity.this, GantiPasswordActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(LupaPasswordVerifikasiEmailActivity.this, GantiPasswordActivity.class);
                        startActivity(intent);
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void showSendEmailAgainDialog(String email){
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Mengirim Email Kembali")
                .setMessage("Kami telah mencoba mengirim ke email, "+email)
                .setCancelable(false)
                .setAnimation(R.raw.loading)
                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
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