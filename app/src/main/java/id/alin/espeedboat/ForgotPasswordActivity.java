package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.LupaPasswordServices;
import id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel.LupaPasswordActivityInstanceFactory;
import id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel.LupaPasswordActivityViewModel;
import id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel.ObjectData.LupaPasswordData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity implements LifecycleOwner {

    Button btnTele, btnEmail;
    EditText email;
    ImageButton btnBack;
    private LinearLayout loadinglayout,contentlayout;

    public static LupaPasswordActivityViewModel lupaPasswordActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViewModel();
        init();
        eventListener();
    }

    /*
    * INIT VIEW MODEL LUPA PASSWORD
    * INPUT DILAKUKAN DALAM BEBERAPA FORM
    * DIAKHIR SEMUA VARIABLE YANG DIINPUT PERLU DIKIRIM LAGI
    * MAKANYA DISIMPAN DULU KE DALAM VIEW MODEL SUPAYA TIDAK HILANG
    * */
    private void initViewModel() {
        ForgotPasswordActivity.lupaPasswordActivityViewModel = new ViewModelProvider(this, new LupaPasswordActivityInstanceFactory())
                                                                        .get(LupaPasswordActivityViewModel.class);
    }

    private void init(){
        email = (EditText) findViewById(R.id.editTextEmailLupaPassword);
        btnEmail = (Button) findViewById(R.id.btnEmailLupaPassword);
        btnTele = (Button) findViewById(R.id.btnTelegramLupaPassword);
        btnBack = (ImageButton) findViewById(R.id.backButton);
        this.loadinglayout = findViewById(R.id.forgotPasswordloading);
        this.contentlayout = findViewById(R.id.contentLayout);
    }

    private void eventListener(){
        btnTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/sistem_speedboat_bot"));
                    startActivity(telegram);
                } catch (Exception e) {
                    Toast.makeText(ForgotPasswordActivity.this, "Aplikasi Telegram Tidak Terpasang!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    sendVerifyEmailAPI(ForgotPasswordActivity.this.email.getText().toString());
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //MODAL EMAIL VERIF
    private void emailVerifSuccess(){
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Email Verifikasi Terkirim")
                .setMessage("Silahkan meng-klik link pada email untuk mengubah password")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent =  new Intent(ForgotPasswordActivity.this,LupaPasswordVerifikasiEmailActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent =  new Intent(ForgotPasswordActivity.this,LupaPasswordVerifikasiEmailActivity.class);
                        startActivity(intent);
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    //VALIDATE EMAIL USER
    private boolean validate(){
        if(email.getText().toString().isEmpty()){
            email.setError("Mohon Masukkan Email Anda");
            return false;
        }
        if(email.getText().toString().length() < 3){
            email.setError("Mohon Masukkan Email dengan Benar");
            return false;
        }
        return true;
    }

    /*
    *
    * MELAKUKAN PEMANGGILAN API KIRIM VERIFY EMAIL
    * */
    private void sendVerifyEmailAPI(String email){
        setStateLoading(true);
        LupaPasswordServices services = ApiClient.getRetrofit().create(LupaPasswordServices.class);
        Call<ServerResponseModels> call = services.sendEmail(email);

        call.enqueue(new Callback<ServerResponseModels>() {
            @Override
            public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                    LupaPasswordData data = ForgotPasswordActivity.lupaPasswordActivityViewModel.getLupaPasswordDataMutableLiveData().getValue();

                    data.setEmail(ForgotPasswordActivity.this.email.getText().toString());
                    ForgotPasswordActivity.lupaPasswordActivityViewModel.setLupaPasswordDataMutableLiveData(data);

                    setStateLoading(false);

                    emailVerifSuccess();
                }else if(response.body().getResponse_code().matches("403")){
                    setStateLoading(false);
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    setStateLoading(false);
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setStateLoading(boolean status){
        if(status){
            this.loadinglayout.setVisibility(View.VISIBLE);
            this.contentlayout.setClickable(false);
        }else{
            this.loadinglayout.setVisibility(View.INVISIBLE);
            this.contentlayout.setClickable(true);
        }
    }
}