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
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.R;

public class BottomSheetJumlahPenumpang extends BottomSheetDialogFragment {
    private MaterialNumberPicker materialNumberPicker;
    private Button btnPilih;

    // ATRIBUTE FORM
    private String form;

    // PRIVATE STATIC FORM YANG AKAN DIISI
    public static final String FORM = "FORM";
    public static final String SPEEDBOAT = "SPEEDBOAT";
    public static final String FERI = "FERI";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogTheme);
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

        form = getArguments().getString(FORM,"");

        this.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form.matches(SPEEDBOAT)){
                    PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
                    pemesananSpeedboatData.setJumlah_penumpang(String.valueOf(materialNumberPicker.getValue()));
                    MainActivity.mainActivityViewModel.setPemesananSpeedboatData(pemesananSpeedboatData);
                }else if(form.matches(FERI)){
                    PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                    pemesananFeriData.setJumlah_penumpang(materialNumberPicker.getValue());
                    MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                }
                dismiss();
            }
        });
    }


}
