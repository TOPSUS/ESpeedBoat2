package id.alin.espeedboat.MyFragment.PemesananFragmentChildFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;

public class BottomSheetFragmentJumlahPenumpang extends BottomSheetDialogFragment {
    private MaterialNumberPicker jumlahpenumpang;
    private Button btnpickjumlahpenumpang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheetjumlahpenumpangpemesananfragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.jumlahpenumpang = view.findViewById(R.id.bottomsheetJumlahPenumpang);
        this.btnpickjumlahpenumpang = view.findViewById(R.id.btnPilihJumlahPenumpang);
        this.btnpickjumlahpenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                pemesananData.setJumlah_penumpang(String.valueOf(BottomSheetFragmentJumlahPenumpang.this.jumlahpenumpang.getValue()));
                MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
                dismiss();
            }
        });
    }

}
