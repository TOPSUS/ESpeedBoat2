package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.R;

public class BottomSheetJumlahPenumpang extends BottomSheetDialogFragment {
    private MaterialNumberPicker materialNumberPicker;
    private Button btnPilih;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheetjumlahpenumpangpemesananfragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.materialNumberPicker = getView().findViewById(R.id.bottomsheetJumlahPenumpang);

        this.btnPilih = getView().findViewById(R.id.btnPilihJumlahPenumpang);
        this.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                pemesananSpeedboatData.setJumlah_penumpang(String.valueOf(materialNumberPicker.getValue()));
                MainActivity.mainActivityViewModel.setPemesananData(pemesananSpeedboatData);
                dismiss();
            }
        });
    }


}
