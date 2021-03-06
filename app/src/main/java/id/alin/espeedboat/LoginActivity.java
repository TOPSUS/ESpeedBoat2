package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.AuthServices;
import id.alin.espeedboat.MySharedPref.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
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

    /*PUBLIC STATIC VARIABLE*/
    public static final String PROFILE_DATA_PARCELABE = "PROFILE_DATA_PARCELABE";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("GOTAMA", "LOGIN ACTIVITY");

        /*INIT SHARED PREf*/
        initSharedPref();

        /*INIT WIDGET HALAMAN LOGIN*/
        initWidget();
    }

    /*METHOD INIT SHARED PREF*/
    private void initSharedPref() {
        if (this.sharedPreferences == null){
            sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        }
        Log.d("check_token_login_init_shared",sharedPreferences.getString(Config.USER_FCM_TOKEN,"KOSONG"));
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

        String html =  "Belum punya akun ? <b>Daftar Sekarang</b> !";

        this.tvregister.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        /*TEXT VIEW REGISTER ADD EVENT*/
        this.tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvregister.setEnabled(false);
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

        /*BUTTON METHOD UNTUK MASUK KE DALAM LUPA PASSWORD*/
        this.tvlupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvlupapassword.setEnabled(false);
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    /*METHOD YANG DIMASUKKAN KE DALAM ONCLICK MASUK BUTTON*/
    private void prepareLoginData() {
        /*VALIDATE*/
        String email = Objects.requireNonNull(this.etemail.getText()).toString();
        String password = Objects.requireNonNull(this.etpassword.getText()).toString();
        String fcm_token = sharedPreferences.getString(Config.USER_FCM_TOKEN,"");

        int validation_point = 4;
        Log.d("check_token_2",sharedPreferences.getString(Config.USER_FCM_TOKEN,"KOSONG"));
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
            postLoginAPI(email,password,fcm_token);
            btnmasuk.startAnimation();
        }

    }

    /*POST API LOGIN KE SERVER*/
    private void postLoginAPI(String email, String password, String fcm_token) {

        /*RETROFIT INIIIATION*/
        AuthServices services = ApiClient.getRetrofit().create(AuthServices.class);
        Call<ServerResponseProfileData> call = services.login(
                email,
                password,
                fcm_token
        );

        call.enqueue(new Callback<ServerResponseProfileData>() {
            @Override
            public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                LoginActivity.this.btnmasuk.revertAnimation();

                /*APABILA SUKSES*/
                if (response.body().getResponse_code().matches("200")) {

                    // GANTI STATUS DARI AUTO_LAUNCH
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            editor = sharedPreferences.edit();
                            editor.putInt(Config.Setting.AUTO_LAUNCH,0);
                            editor.apply();
                        }
                    });

                    /*MEMBERIKAN DATA KE DALAM SHARED PREF*/
                    setSharedPreferenceData(response.body());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                }

                /*APABILA GAGAL*/
                else if (response.body().getResponse_code().matches("401") && response.body().getStatus().matches("error_input")) {
                    modalShowError(response.body().getError().parseErrorAll());
                }

                /*ELSE*/
                else {
                    modalShowError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseProfileData> call, Throwable t) {
                LoginActivity.this.btnmasuk.revertAnimation();
                Log.d("TEST", t.getMessage());
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
                .setAnimation(R.raw.animation_boat_2)
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

    /*MENYIMPAN KE DALAM SHARED PREFERENCES*/
    private void setSharedPreferenceData(ServerResponseProfileData data){
        editor = sharedPreferences.edit();
        editor.putString(Config.USER_ID,data.getUser_id());

        /*
        * MEMBERIKAN PREFIX HEADER KEPADA TOKEN
        * */
        StringBuilder token = new StringBuilder();
        token.append("Bearer ");
        token.append(data.getToken());

        editor.putString(Config.USER_TOKEN,token.toString());
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

    @Override
    protected void onResume() {
        super.onResume();

        tvregister.setEnabled(true);
        tvlupapassword.setEnabled(true);
    }
}