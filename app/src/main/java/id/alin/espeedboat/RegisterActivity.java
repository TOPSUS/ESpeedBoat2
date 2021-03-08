package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {

    /*WIDGET DI HALAMAN REGISTER*/
    private MaterialEditText metnama, metemail, metnohp, metalamat, metpassowrd, metconfirmpassord;

    private CircularProgressButton circularProgressButton;

    private AutoCompleteTextView dropdownjeniskelamin;

    /*VARIABNLE JENIS KELAMIN*/
    private String jenis_kelamin;

    /*MINI FRAGMENT BOTTOM SHEET DIALGO*/
    private BottomSheetDialog bottomSheetDialog;

    /*PRIVATE STATIC FIELD*/
    private static final int MIN_INPUT_GENERAL = 4;
    private static final int MAX_INPUT_GENERAL = 50;
    private static final int MIN_INPUT_NOHP = 10;
    private static final int MAX_INPUT_NOHP = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*
        * INIT SEMUA WIDGET DI LAMAN REGISTER
        * */
        initWidget();
    }

    /*
    * METHOD YANG DIGUNAKAN NTUK MELAKUYKAN INIT WIDGET
    * */
    private void initWidget(){

        /*INIT MET NAMA*/
        if(this.metnama == null){
            this.metnama = findViewById(R.id.metRegisterActivityNama);
        }
        this.metnama.setMinCharacters(MIN_INPUT_GENERAL);
        this.metnama.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET EMAIL*/
        if(this.metemail == null){
            this.metemail = findViewById(R.id.metRegisterActivityEmail);
        }
        this.metemail.setMinCharacters(MIN_INPUT_GENERAL);
        this.metemail.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT NO HP*/
        if(this.metnohp == null){
            this.metnohp = findViewById(R.id.metRegisterActivityNohp);
        }
        this.metnohp.setMinCharacters(MIN_INPUT_NOHP);
        this.metnohp.setMaxCharacters(MAX_INPUT_NOHP);

        /*INIT MET ALAMAT*/
        if(this.metalamat == null){
            this.metalamat = findViewById(R.id.metRegisterActivityAlamat);
        }
        this.metalamat.setMinCharacters(MIN_INPUT_GENERAL);
        this.metalamat.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET PASSWORD*/
        if(this.metpassowrd == null){
            this.metpassowrd = findViewById(R.id.metRegisterActivityPassword);
        }
        this.metpassowrd.setMinCharacters(MIN_INPUT_GENERAL);
        this.metpassowrd.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT MET REGISTER PASSWORD*/
        if(this.metconfirmpassord == null){
            this.metconfirmpassord = findViewById(R.id.metRegisterActivityConfirmpassword);
        }
        this.metconfirmpassord.setMinCharacters(MIN_INPUT_GENERAL);
        this.metconfirmpassord.setMaxCharacters(MAX_INPUT_GENERAL);

        /*INIT JENIS KELAMIN*/
            /*MENGISI JENIS KELAMIN*/
            String[] type = new String[] {"Laki - Laki","Perempuan"};

            ArrayAdapter<String> adapter =new ArrayAdapter<>(this,R.layout.text_view_dropdown_item,type);

            /*INIT DROP DOWN*/
            if(this.dropdownjeniskelamin == null){
                dropdownjeniskelamin = findViewById(R.id.filled_exposed_dropdown);
            }

            /*MEMASUKKAN */
            dropdownjeniskelamin.setAdapter(adapter);
            dropdownjeniskelamin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        RegisterActivity.this.jenis_kelamin = "Laki - laki";
                    }
                    else if(position == 1){
                        RegisterActivity.this.jenis_kelamin = "Perempuan";
                    }
                }
            });

        /*INIT CIRCULAR BUTTON*/
        circularProgressButton = findViewById(R.id.btnRegisterActivityDaftar);


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        /*INIT MINI FRAGMENT REGISTER BOTTOM SHEET*/
        if(this.bottomSheetDialog == null){
            bottomSheetDialog = new BottomSheetDialog(RegisterActivity.this,R.style.BottomSheetDialogTheme);
        }

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mini_fragment_register_activity_bottom_sheet,findViewById(R.id.minifragmentRegisterActivityBottomSheet));

        bottomSheetDialog.setContentView(view);
    }


}