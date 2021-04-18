package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityViewModel;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.R;

public class JasaDialogFragment extends DialogFragment {
    private Button btnpenumpang, btnkendaraan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jasa_dialog,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        initWidget();
    }

    private void initWidget() {
        this.btnpenumpang = getView().findViewById(R.id.penumpang);
        this.btnkendaraan = getView().findViewById(R.id.kendaraan);

        this.btnpenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                pemesananFeriData.setTipe_jasa(FeriFragment.PENUMPANG);
                pemesananFeriData.setId_golongan_kendaraan(0);
                pemesananFeriData.setGologan_kendaraan("");
                MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                dismiss();
            }
        });

        this.btnkendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                pemesananFeriData.setTipe_jasa(FeriFragment.KENDARAAN);
                pemesananFeriData.setId_golongan_kendaraan(0);
                pemesananFeriData.setGologan_kendaraan("");
                MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                dismiss();
            }
        });
    }
}
