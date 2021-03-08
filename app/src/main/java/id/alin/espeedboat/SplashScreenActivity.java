package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MySharedPref.Config;

public class SplashScreenActivity extends AppCompatActivity {
    /*SHARED PREFERENCES*/
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*INIT SHARED PREFERENCES*/
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        /*AMBIL STATUS DARI SHARED PREF*/
        String status = sharedPreferences.getString(Config.USER_ID,"");

        /*CEK APAKAH SUDAH LOGIN ATAU BELUM*/
        checkAuth(status);
    }

    private void checkAuth(String status){
        if(status == ""){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
    }
}