package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import id.alin.espeedboat.Fragment.ProfileFragment;

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
                Toast.makeText(MyProfileActivity.this, "Pengaturan Akun User", Toast.LENGTH_SHORT).show();
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "Kelola Password", Toast.LENGTH_SHORT).show();
            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "Kelola PIN", Toast.LENGTH_SHORT).show();
            }
        });

        tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/mca_1805551097_bot"));
                    startActivity(telegram);
                } catch (Exception e) {
                    Toast.makeText(MyProfileActivity.this, "Aplikasi Telegram Tidak Terpasang!", Toast.LENGTH_LONG).show();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "Logout Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}