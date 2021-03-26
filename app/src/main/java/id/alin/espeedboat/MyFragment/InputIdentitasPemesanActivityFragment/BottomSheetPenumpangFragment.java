package id.alin.espeedboat.MyFragment.InputIdentitasPemesanActivityFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;
import id.alin.espeedboat.RegisterActivity;

public class BottomSheetPenumpangFragment extends BottomSheetDialogFragment {
    private ImageButton close;
    private int index;
    private String nama;
    private String nomor_identitas;

    /*STATUS BTN SIMPAN*/
    private boolean btnsimpan_clicked;

    /*FIELD NAMA DAN NO IDENTITAS*/
    private TextInputEditText tvnama, tvnoidentitas;

    /*FIELD JENIS ID_CARD*/
    private AutoCompleteTextView type_id_card;

    /*BUTTON SIMPAN*/
    private MaterialButton btnsimpan;

    /*ANTI DOUBLE CLICK*/
    private long mLastClickTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_penumpang,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*AMBIL DATA */
        nama = getArguments().getString(InputIdentitasPemesanActivity.NAMA_PENUMPANG,"");
        nomor_identitas = getArguments().getString(InputIdentitasPemesanActivity.NOMOR_IDENTITAS,"");
        index = getArguments().getInt(InputIdentitasPemesanActivity.INDEX_PENUMPANG);

        close = view.findViewById(R.id.closebutton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /*MEMBUAT PILIHAN ID_CARD*/
        this.type_id_card = getView().findViewById(R.id.filled_exposed_dropdown);

        String[] type = new String[]{"KTP", "SIM","Kartu Pelajar"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.text_view_dropdown_item, type);
        this.type_id_card.setAdapter(adapter);

        this.tvnama = view.findViewById(R.id.namapengguna);
        this.tvnama.setText(nama);

        this.tvnoidentitas = view.findViewById(R.id.identitaspengguna);
        tvnoidentitas.setText(nomor_identitas);

        btnsimpan_clicked = false;
        this.btnsimpan = view.findViewById(R.id.tombolsimpan);
        this.btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    Log.d("kondisi","kondisi 1");
                }
                else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    Log.d("kondisi","kondisi 2");
                    if(doValidate()){
                        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
                        penumpangDataList.get(index).setNama_pemegang_ticket(tvnama.getText().toString());
                        penumpangDataList.get(index).setNo_id_card(tvnoidentitas.getText().toString());
                        penumpangDataList.get(index).setType_id_card(type_id_card.getText().toString());
                        Toast.makeText(getContext(),type_id_card.getText().toString() , Toast.LENGTH_SHORT).show();
                        InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangDataList);
                        btnsimpan_clicked = true;
                        dismiss();
                    }
                }
            }
        });
    }

    private boolean doValidate() {
        int validation = 1;

        if(this.tvnama.getText().toString().matches("")){
            validation-=1;
            this.tvnama.setError("Masukkan nama penumpang");
        }

        if(this.tvnoidentitas.getText().toString().matches("")){
            validation-= 1;
            this.tvnoidentitas.setError("Masukkan nomor identitas");
        }

        if(this.type_id_card.getText().toString().matches("")){
            validation-=1;
            this.type_id_card.setError("");
        }

        if(validation == 1){
            Log.d("kondisi","kondisi 3");
            return true;
        }else{
            Log.d("kondisi","kondisi 4");
            return false;
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        if(index == 0 && !btnsimpan_clicked){
            btnsimpan_clicked = false;
            ((InputIdentitasPemesanActivity)getContext()).toggle.setToggleOff();
            List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
            penumpangDataList.get(index).setNama_pemegang_ticket("");
            penumpangDataList.get(index).setNo_id_card("");
            InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangDataList);
        }

        super.onDismiss(dialog);
    }

}

