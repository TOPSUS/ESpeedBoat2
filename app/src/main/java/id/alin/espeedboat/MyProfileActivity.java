package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {

    ImageButton backButton;
    LinearLayout akun, pass, pin, tele, logout;

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
        backButton = (ImageButton)findViewById(R.id.backButton);
        akun = (LinearLayout)findViewById(R.id.kelolaAkun);
        pass = (LinearLayout)findViewById(R.id.kelolaPassword);
        pin = (LinearLayout)findViewById(R.id.kelolaPin);
        tele = (LinearLayout)findViewById(R.id.kelolaTelegram);
        logout = (LinearLayout)findViewById(R.id.logoutt);
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
                Intent intent = new Intent(MyProfileActivity.this, AddPinActivity.class);
                startActivity(intent);
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
                                Intent i = new Intent(MyProfileActivity.this, LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(MyProfileActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                                MyProfileActivity.this.finish();
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
}