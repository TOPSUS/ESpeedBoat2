package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.PemesananFragmentChildFragment.PemesananBottomSheetFragmentAsal;
import id.alin.espeedboat.MyFragment.PemesananFragmentChildFragment.PemesananBottomSheetFragmentTujuan;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.MyViewModel.PemesananFragmentViewModel.PemesananFragmentInstanceFactory;
import id.alin.espeedboat.MyViewModel.PemesananFragmentViewModel.PemesananFragmentViewModel;
import id.alin.espeedboat.R;

public class PemesananFragment extends Fragment implements LifecycleOwner {

    /*VIEW MODEL PEMESANAN FRAGMNT*/
    public static PemesananFragmentViewModel pemesananFragmentViewModel;

    /*WIDGET DI HALAMAN FRAGMENT PEMESANAN*/
    private MaterialEditText metasal, mettujuan, mettanggal, metjumlahpenumpang;
    private Button btncari;

    /*THE CHILD FRAGMENTS*/
    PemesananBottomSheetFragmentAsal pemesananBottomSheetFragmentAsal;

    /*DATABASE*/
    private DatabaeESpeedboat databaeESpeedboat;

    /*CHILD FRAGMENT TAGS*/
    public static String FRAGMENT_PEMESANAN_CHILD_ASAL = "FRAGMENT_PEMESANAN_CHILD_ASAL";
    public static String FRAGMENT_PEMESANAN_CHILD_TUJUAN = "FRAGMENT_PEMESANAN_CHILD_TUJUAN";

    /*PELABUHAN ENTITIES*/
    public List<PelabuhanEntity> pelabuhanEntities;
    /*
     *
     * PEMESANAN DATA
     * MERUPAKAN PROPERTIES YANG AKAN DIPERTUKARKAN DENGAN VIEW MODEL
     * INI MEMUNGKINKAN UNTUK MENGUBAH DATA FRAGMENT PEMESANAN SATU PERSATU
     * DAN APA BILA TERJADI PERUBAHAN KONFIGURASI ULANG (ROTASI / GELAP KE TERANG)
     * DATA AKAN TETAP TERSIMPAN
     * */
    private PemesananData pemesananData;

    public PemesananFragment() {
        // HARUS TETAP KOSONG
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemesanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
        initDatabase();
        initWidget();
    }

    private void initViewModel() {
        PemesananFragment.pemesananFragmentViewModel = new ViewModelProvider(this, new PemesananFragmentInstanceFactory())
                .get(PemesananFragmentViewModel.class);
    }

    private void initDatabase() {
        this.databaeESpeedboat = DatabaeESpeedboat.createDatabase(getContext());
    }

    private void initWidget() {

        /*MET ASAL*/
        if (this.metasal == null) {
            this.metasal = getView().findViewById(R.id.metFragmentPemesananAsal);
        }

        /*MET TUJUAN*/
        if (this.mettujuan == null) {
            this.mettujuan = getView().findViewById(R.id.metFragmentPemesananTujuan);
        }

        /*MET TANGGAK*/
        if (this.mettanggal == null) {
            this.mettanggal = getView().findViewById(R.id.metFragmentPemesananTanggal);
        }

        /*MET JUMLAH PENUMPANG*/
        if (this.metjumlahpenumpang == null) {
            this.metjumlahpenumpang = getView().findViewById(R.id.metFragmentPemesananPenumpang);
        }

        /*BUTTON CARI*/
        if (this.btncari == null) {
            this.btncari = getView().findViewById(R.id.btnFragmentPemesananCari);
        }

        /*CHECKS VIEW MODEL PEMESANAN*/
        if (MainActivity.mainActivityViewModel.getPemesananLiveData() != null) {
            Log.d("PEMESANAN", "MASUK SINI");
            MainActivity.mainActivityViewModel.getPemesananLiveData().observe(this, new Observer<PemesananData>() {
                @Override
                public void onChanged(PemesananData pemesananData) {
                    if (pemesananData.getAsal().matches("") || pemesananData.getAsal() == null) {
                        PemesananFragment.this.metasal.setText("");
                    } else {
                        Log.d("PEMESANAN", "AMBIL DARI VIEW MODEL");
                        PemesananFragment.this.metasal.setText(pemesananData.getAsal());
                    }

                    if (pemesananData.getTujuan().matches("") || pemesananData.getTujuan() == null) {
                        PemesananFragment.this.mettujuan.setText("");
                    } else {
                        PemesananFragment.this.mettujuan.setText(pemesananData.getTujuan());
                    }

                    if (pemesananData.getTanggal().matches("") || pemesananData.getTanggal() == null) {
                        PemesananFragment.this.mettanggal.setText("");
                    } else {
                        PemesananFragment.this.mettanggal.setText(pemesananData.getTanggal());
                    }

                    if (pemesananData.getJumlah_penumpang().matches("") || pemesananData.getJumlah_penumpang() == null) {
                        PemesananFragment.this.metjumlahpenumpang.setText("");
                    } else {
                        PemesananFragment.this.metjumlahpenumpang.setText(pemesananData.getJumlah_penumpang());
                    }

                    /*MENYIMPAN KE DALAM PROPERTI*/
                    PemesananFragment.this.pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                }
            });
        } else {
            Log.d("PEMESANAN", "MASUK SINI 2");
            PemesananData pemesananData = new PemesananData();
            MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
            MainActivity.mainActivityViewModel.getPemesananLiveData().observe(this, new Observer<PemesananData>() {
                @Override
                public void onChanged(PemesananData pemesananData) {
                    if (pemesananData.getAsal().matches("") || pemesananData.getAsal() == null) {
                        PemesananFragment.this.metasal.setText("");
                    } else {
                        PemesananFragment.this.metasal.setText(pemesananData.getAsal());
                    }

                    if (pemesananData.getTujuan().matches("") || pemesananData.getTujuan() == null) {
                        PemesananFragment.this.mettujuan.setText("");
                    } else {
                        PemesananFragment.this.mettujuan.setText(pemesananData.getTujuan());
                    }

                    if (pemesananData.getTanggal().matches("") || pemesananData.getTanggal() == null) {
                        PemesananFragment.this.mettanggal.setText("");
                    } else {
                        PemesananFragment.this.mettanggal.setText(pemesananData.getTanggal());
                    }

                    if (pemesananData.getJumlah_penumpang().matches("") || pemesananData.getJumlah_penumpang() == null) {
                        PemesananFragment.this.metjumlahpenumpang.setText("");
                    } else {
                        PemesananFragment.this.metjumlahpenumpang.setText(pemesananData.getJumlah_penumpang());
                    }

                    /*MENYIMPAN KE DALAM PROPERTI*/
                    PemesananFragment.this.pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                }
            });
        }

        /*MEMBERIKAN LISTENER KE DALAM WIDGET*/
        this.metasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAsal();
            }
        });

        this.mettujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTujuan();
            }
        });
    }

    private void selectAsal() {
        PemesananBottomSheetFragmentAsal pemesananBottomSheetFragmentAsal = new PemesananBottomSheetFragmentAsal();
        pemesananBottomSheetFragmentAsal.showNow(getChildFragmentManager(), FRAGMENT_PEMESANAN_CHILD_ASAL);
    }

    private void selectTujuan() {
        PemesananBottomSheetFragmentTujuan pemesananBottomSheetFragmentTujuan = new PemesananBottomSheetFragmentTujuan();
        pemesananBottomSheetFragmentTujuan.showNow(getChildFragmentManager(), FRAGMENT_PEMESANAN_CHILD_TUJUAN);
    }
}