package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.alin.espeedboat.MyAdapter.PenumpangAdapter;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModel;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModelFactory;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;

public class InputIdentitasPemesanActivity extends AppCompatActivity implements LifecycleOwner {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PenumpangAdapter penumpangAdapter;
    public static InputIdentitasPemesanActivityViewModel inputIdentitasPemesanActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_identitas_pemesan);

        initViewModel();

    }

    /*MEMBENTUK VIEW MODEL*/
    private void initViewModel() {
        /*MEMBUYAT VIEW MODEL*/
        if(inputIdentitasPemesanActivityViewModel == null){
            inputIdentitasPemesanActivityViewModel = new ViewModelProvider(this, new InputIdentitasPemesanActivityViewModelFactory())
                                                            .get(InputIdentitasPemesanActivityViewModel.class);
        }

        /*LIVE DATA LIST PENUMPANG*/

        /*MENGAMBIL DATA PESANAN DARI VIEW MODEL MAINACTIVITY*/
        PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        /*MEMASUKKAN DATA PEMESAN KE TRANSAKSI DATA LIVE DATA*/
        TransaksiData transaksiData = inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
        transaksiData.setId_jadwal(pemesananData.getJadwalEntity().getId());
        transaksiData.setId_user(Long.parseLong(profileData.getUser_id()));
        transaksiData.setTanggal(pemesananData.getTanggal_variable());
        transaksiData.setTotal_biaya((Long.parseLong(pemesananData.getJadwalEntity().getHarga()) * Long.parseLong(pemesananData.getJumlah_penumpang())));
        inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);

        List<PenumpangData> penumpangDataList = inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
        Toast.makeText(this, String.valueOf(penumpangDataList.size()), Toast.LENGTH_SHORT).show();
    }
}