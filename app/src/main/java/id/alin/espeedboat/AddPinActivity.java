package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddPinActivity extends AppCompatActivity {

    Button btnUpdate, btnCancel;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pin);
        init();
        eventListener();
    }

    private void init(){
        backButton = (ImageButton) findViewById(R.id.backButton);
        btnUpdate = (Button) findViewById(R.id.btnAddPin);
        btnCancel = (Button) findViewById(R.id.btnCancelAddPin);
    }

    private void eventListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPinActivity.this, "Update!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}