package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyFireBase.MyFireBaseCloudMessaging;
import id.alin.espeedboat.MySharedPref.Config;

public class SplashScreenActivity extends AppCompatActivity {
    /*SHARED PREFERENCES*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*INIT SHARED PREFERENCES*/
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // INIT FIREBASE SERVICE
        if(sharedPreferences.getString(Config.USER_FCM_TOKEN,"").matches("")){
            initFireBase();
            Log.d("check_token_splash",sharedPreferences.getString(Config.USER_FCM_TOKEN,"KOSONG"));
        }

        /*AMBIL STATUS DARI SHARED PREF*/
        String status = sharedPreferences.getString(Config.USER_ID,"");
        checkAuth(status);
    }

    private void initFireBase() {
        // PERTAMA CHECK TERLEBIH DAHULU APAKAH SUDAH ADA TOKEN ATAU BELUM
            FirebaseMessaging.getInstance().subscribeToTopic(MyFireBaseCloudMessaging.BROADCAST).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    editor.putString(Config.USER_FCM_TOKEN, FirebaseMessaging.getInstance().getToken().getResult());
                    editor.apply();
                }
            });
    }

    private void checkAuth(String status){
        if(status.equals("")){
            /*CEK APAKAH SUDAH LOGIN ATAU BELUM*/
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
        else{
            /*CEK APAKAH SUDAH LOGIN ATAU BELUM*/
            new Handler(getMainLooper()).postDelayed(new Runnable() {
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