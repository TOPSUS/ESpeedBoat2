package id.alin.espeedboat.MyProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.UserServices;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyProfileActivity extends AppCompatActivity {

    ImageButton backButton;
    LinearLayout akun, pass, pin, tele, logout;
    CircleImageView circleImageView;
    TextView tvNameBig, tvEmailBig, tvName, tvEmail, tvTelepon, tvGender, tvAddress;
    ProgressDialog dialog;
    String myPin;

    /*SHARED PREFERENCE APLIKASI*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        /*DISABLE NIGHT MODE*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        eventListener();
    }

    private void init(){
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        //PROGRES BAR INIT
        dialog = new ProgressDialog(MyProfileActivity.this);
        dialog.setCancelable(false);


        backButton = (ImageButton)findViewById(R.id.backButton);
        akun = (LinearLayout)findViewById(R.id.kelolaAkun);
        pass = (LinearLayout)findViewById(R.id.kelolaPassword);
        pin = (LinearLayout)findViewById(R.id.kelolaPin);
        tele = (LinearLayout)findViewById(R.id.kelolaTelegram);
        logout = (LinearLayout)findViewById(R.id.logoutt);

        //Account Info Init
        circleImageView = (CircleImageView) findViewById(R.id.civMyProfile);
        tvNameBig = (TextView) findViewById(R.id.tvnameBig);
        tvEmailBig = (TextView) findViewById(R.id.tvemailBig);
        tvName = (TextView) findViewById(R.id.tvMyProfileName);
        tvEmail = (TextView) findViewById(R.id.tvMyProfileEmail);
        tvTelepon = (TextView) findViewById(R.id.tvMyProfileTelepon);
        tvGender = (TextView) findViewById(R.id.tvMyProfileGender);
        tvAddress = (TextView) findViewById(R.id.tvMyprofileAddress);

        MainActivity.mainActivityViewModel.getProfileLiveData().observe(this, new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData data) {
                MyProfileActivity.this.tvNameBig.setText(data.getName());
                MyProfileActivity.this.tvEmailBig.setText(data.getEmail());
                MyProfileActivity.this.tvName.setText(data.getName());
                MyProfileActivity.this.tvEmail.setText(data.getEmail());
                MyProfileActivity.this.tvTelepon.setText(data.getNohp());
                MyProfileActivity.this.tvGender.setText(data.getJeniskelamin());
                MyProfileActivity.this.tvAddress.setText(data.getAlamat());
                MyProfileActivity.this.myPin = data.getPin();
                StringBuilder url = new StringBuilder(ApiClient.BASE_IMAGE_USER);
                url.append(data.getFoto());

                Glide.with(MyProfileActivity.this).load(url.toString())
                        .placeholder(R.drawable.user_placeholder)
                        .into(MyProfileActivity.this.circleImageView);

            }
        });
    }

    private void eventListener(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
               startActivity(intent);
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, EditPasswordActivity.class);
                startActivity(intent);
            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPin.isEmpty()){
                    Intent intent = new Intent(MyProfileActivity.this, AddPinActivity.class);
                    startActivity(intent);
                }else if(!(myPin.isEmpty())){
                    Intent intent = new Intent(MyProfileActivity.this, EditPinActivity.class);
                    startActivity(intent);
                }

            }
        });

        tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/sistem_speedboat_bot"));
                    startActivity(telegram);
                } catch (Exception e) {
                    Toast.makeText(MyProfileActivity.this, "Aplikasi Telegram Tidak Terpasang!", Toast.LENGTH_LONG).show();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog mDialog = new MaterialDialog.Builder(MyProfileActivity.this)
                        .setTitle("Konfirmasi Logout")
                        .setMessage("Apakah anda yakin untuk logout?")
                        .setCancelable(false)
                        .setAnimation(R.raw.animation_boat_2)
                        .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                logoutFromApi();
                            }
                        })
                        .setNegativeButton("Batal", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }

                        })
                        .build();

                // Show Dialog
                mDialog.show();

            }
        });
    }

    private void logoutFromApi(){
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
        dialog.setMessage("Processing");
        dialog.show();
        UserServices services = ApiClient.getRetrofit().create(UserServices.class);
        Call<ServerResponseProfileData> call = services.logout(
                data.getToken(),
                data.getUser_id()

        );

        call.enqueue(new Callback<ServerResponseProfileData>() {
            @Override
            public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                clearSharedPreference();

                // TRUNCATE DATA NOTIFICATION
                DatabaeESpeedboat.createDatabase(MyProfileActivity.this).notificationDAO().truncateNotification();

                dialog.dismiss();
                Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ServerResponseProfileData> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    // TIDAK BOLEH MENGHAPUS FCM_TOKEN
    private void clearSharedPreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Config.USER_TOKEN);
        editor.remove(Config.USER_FOTO);
        editor.remove(Config.USER_JENIS_KELAMIN);
        editor.remove(Config.USER_NOHP);
        editor.remove(Config.USER_EMAIL);
        editor.remove(Config.USER_PIN);
        editor.remove(Config.USER_CHAT_ID);
        editor.remove(Config.USER_ALAMAT);
        editor.remove(Config.USER_NAMA);
        editor.remove(Config.USER_ID);
        editor.apply();
    }
}