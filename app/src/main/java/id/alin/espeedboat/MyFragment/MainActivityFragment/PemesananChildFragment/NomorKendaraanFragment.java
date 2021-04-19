package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.R;

public class NomorKendaraanFragment extends DialogFragment {
    private EditText editText;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nomor_kendaraan,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.button = getView().findViewById(R.id.btnpilih);
        this.editText= getView().findViewById(R.id.etnomorkendaraan);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doValidate()){
                    PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                    pemesananFeriData.setNomor_kendaraan(editText.getText().toString());
                    MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                    dismiss();
                }
            }
        });
    }

    private boolean doValidate() {
        int validation = 1;
        if(this.editText.getText().toString().matches("")){
            validation -= 1;
            this.editText.setError("");
        }

        return validation == 1;
    }
}
