package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rengwuxian.materialedittext.MaterialEditText;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenDialog;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;

public class PemesananJadwalFragment extends Fragment implements LifecycleOwner {

    /*WIDGET YANG DIGUNAKAN PADA LAMAN*/
    private MaterialEditText metdari;

    public PemesananJadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemesanan_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidget();
    }

    /*MELAKUKAN INIT WIDGET HALAMAN*/
    private void initWidget(){
        this.metdari = getView().findViewById(R.id.metPemesananJadwalFragmentDari);
        this.metdari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenDialog fullscreenDialog = FullscreenDialog.createNewInstance();
                fullscreenDialog.showNow(getChildFragmentManager(),"TAG");
            }
        });
    }

    /*INIT VIEW MODEL DAN MEMASANG OBSERVER*/
    private void initViewModel() {

        /*INIT PERTAMA KALI VIEW MODEL PEMESANAN SUPAYA TIDAK KOSONG DATA PEMESANANNYA*/
        MainActivity.mainActivityViewModel.setPemesananData(new PemesananData());


        /*MELAKUKAN OBSERVER KE DALAM VIEW MODEL*/
        MainActivity.mainActivityViewModel.getPemesananLiveData().observe(this, new Observer<PemesananData>() {
            @Override
            public void onChanged(PemesananData pemesananData) {
                metdari.setText(pemesananData.getAsal());
            }
        });
    }
}