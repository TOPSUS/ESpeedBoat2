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
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenDialogAsal;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenDialogTujuan;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;

public class PemesananJadwalFragment extends Fragment implements LifecycleOwner {

    /*WIDGET YANG DIGUNAKAN PADA LAMAN*/
    private MaterialEditText metasal;
    private MaterialEditText mettujuan;
    private MaterialEditText mettanggal;
    private MaterialEditText metjumlahpenumpang;

    /*FRAGMENT TAGS*/
    public static final String FRAGMENT_FULLSCREEN_ASAL = "FRAGMENT_FULLSCREEN_ASAL";
    public static final String FRAGMENT_FULLSCREEN_TUJUAN = "FRAGMENT_FULLSCREEN_TUJUAN";

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
        this.metasal = getView().findViewById(R.id.metPemesananJadwalFragmentDari);
        this.metasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenDialogAsal fullscreenDialogAsal = FullscreenDialogAsal.createNewInstance();
                fullscreenDialogAsal.showNow(getChildFragmentManager(),FRAGMENT_FULLSCREEN_ASAL);
            }
        });

        this.mettujuan = getView().findViewById(R.id.metPemesananJadwalFragmentTujuan);
        this.mettujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenDialogTujuan fullscreenDialogTujuan = FullscreenDialogTujuan.createNewInstance();
                fullscreenDialogTujuan.showNow(getChildFragmentManager(),FRAGMENT_FULLSCREEN_TUJUAN);
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
                metasal.setText(pemesananData.getAsal());
                mettujuan.setText(pemesananData.getTujuan());
            }
        });
    }
}