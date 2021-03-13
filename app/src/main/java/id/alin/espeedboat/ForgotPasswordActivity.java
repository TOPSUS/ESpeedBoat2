package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnTele, btnEmail;
    EditText email;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
        eventListener();
    }

    private void init(){
        email = (EditText) findViewById(R.id.editTextEmailLupaPassword);
        btnEmail = (Button) findViewById(R.id.btnEmailLupaPassword);
        btnTele = (Button) findViewById(R.id.btnTelegramLupaPassword);
        btnBack = (ImageButton) findViewById(R.id.backButton);
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
                    emailVerifSuccess();
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
                        ForgotPasswordActivity.this.finish();
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
}