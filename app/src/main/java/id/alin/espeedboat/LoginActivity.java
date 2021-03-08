package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ErrorProfileData;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ProfileData;
import id.alin.espeedboat.MyRetrofit.Services.AuthServices;
import id.alin.espeedboat.MySharedPref.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    /*SHARED PREFERENCE APLIKASI*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /*WIDGET HALAMAN LOGIN*/
    private MaterialEditText etemail, etpassword;
    private TextView tvlupapassword, tvregister;
    private CircularProgressButton btnmasuk;

    /*PRIVATE VARIABEL*/
    private static final int MIN_EDIT_EMAIL = 3;
    private static final int MAX_EDIT_EMAIL = 50;
    private static final int MIN_EDIT_PASSWORD = 3;
    private static final int MAX_EDIT_PASSWORD = 50;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*INIT WIDGET HALAMAN LOGIN*/
        initWidget();

        /*INIT SHARED PREf*/
        initSharedPref();
    }

    /*METHOD INIT SHARED PREF*/
    private void initSharedPref() {
        if (this.sharedPreferences == null){
            sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        }
    }

    /*METHOD INIT WIDGET HALAMAN LOGIN*/
    private void initWidget() {
        if(this.etemail == null){
            this.etemail = findViewById(R.id.metLoginActivityUsername);
            this.etemail.setMinCharacters(MIN_EDIT_EMAIL);
            this.etemail.setMaxCharacters(MAX_EDIT_EMAIL);
        }

        if(this.etpassword == null){
            this.etpassword = findViewById(R.id.metLoginActivityPassword);
            this.etpassword.setMinCharacters(MIN_EDIT_PASSWORD);
            this.etpassword.setMaxCharacters(MAX_EDIT_PASSWORD);
        }

        if(this.tvlupapassword == null){
            this.tvlupapassword = findViewById(R.id.tvLoginActivityLupapassword);
        }

        if(this.btnmasuk == null){
            this.btnmasuk = findViewById(R.id.btnLoginActivityMasuk);
        }

        if(this.tvregister == null){
            this.tvregister = findViewById(R.id.tvLoginActivityRegister);
        }

        /*TEXT VIEW REGISTER ADD EVENT*/
        this.tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        /*BUTTON METHOD UNTUK MASUK KE DALAM APLIKASI*/
        this.btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.prepareLoginData();
            }
        });


    }

    /*METHOD YANG DIMASUKKAN KE DALAM ONCLICK MASUK BUTTON*/
    private void prepareLoginData() {
        /*VALIDATE*/
        String email = Objects.requireNonNull(this.etemail.getText()).toString();
        String password = Objects.requireNonNull(this.etpassword.getText()).toString();
        int validation_point = 4;

        /*CEK */
        if(email.matches("")){
            this.etemail.setError("Mohon inputkan email anda");
            validation_point-=1;
        }

        if(password.matches("")){
            this.etpassword.setError("Mohon inputkan password anda");
            validation_point-=1;
        }

        if(password.length() <MIN_EDIT_PASSWORD || password.length() > MAX_EDIT_PASSWORD){
            validation_point-=1;
        }

        if(email.length() <MIN_EDIT_EMAIL || email.length() > MAX_EDIT_EMAIL){
            validation_point-=1;
        }

        if(validation_point == 4){
            postLoginAPI(email,password);
            btnmasuk.startAnimation();
        }

    }

    /*POST API LOGIN KE SERVER*/
    private void postLoginAPI(String email, String password){

        /*RETROFIT INIIIATION*/
        AuthServices services = ApiClient.getRetrofit().create(AuthServices.class);
        Call<ProfileData> call = services.login(
            email,
            password
        );

        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                LoginActivity.this.btnmasuk.revertAnimation();
                if (response.body().getResponse_code().matches("200")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(response.body().getResponse_code().matches("401") && response.body().getStatus().matches("error_input")){
                    modalShowError(response.body().getError().parseErrorAll());
                }
                else{
                    modalShowError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                LoginActivity.this.btnmasuk.revertAnimation();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TEST",t.getMessage());
            }
        });
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
                .setNegativeButton("RESET", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        LoginActivity.this.etemail.setText("");
                        LoginActivity.this.etpassword.setText("");
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
}