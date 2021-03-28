package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.zcw.togglebutton.ToggleButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyAdapter.PenumpangAdapter;
import id.alin.espeedboat.MyFragment.InputIdentitasPemesanActivityFragment.BottomSheetPenumpangFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.BottomSheetJumlahPenumpang;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.PemesananServices;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModel;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModelFactory;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputIdentitasPemesanActivity extends AppCompatActivity implements LifecycleOwner {
    private RecyclerView recyclerView;
    private PenumpangAdapter penumpangAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static InputIdentitasPemesanActivityViewModel inputIdentitasPemesanActivityViewModel;

    /*WIDGET*/
    private TextView tvasaltujuan, tvdetailjadwal, tvnamapemesan, tvemailpemesan, tvnomorpemesan,
                        tvtotalbiaya;
    private Button btnlanjutkan;
    private LinearLayout loading;

    public ToggleButton toggle;

    /*PEMESAN DIAMBIL DARI PROFILEDATA MAINACTIVITY*/
    private ProfileData pemesan;

    /*PUBLIC STATIC KEY*/
    public static  final String INDEX_PENUMPANG = "INDEX_PENUMPANG";
    public static  final String NAMA_PENUMPANG = "NAMA_PENUMPANG";
    public static  final String NOMOR_IDENTITAS = "NOMOR_IDENTITAS";

    /*BUTTON ANTI DOUBLE CLICK*/
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_identitas_pemesan);

        initViewModel();
        initWidget();
        fillRecyclerView();
    }

    /*MEMBENTUK VIEW MODEL*/
    private void initViewModel() {
        /*MEMBUYAT VIEW MODEL*/
        if(inputIdentitasPemesanActivityViewModel == null){
            inputIdentitasPemesanActivityViewModel = new ViewModelProvider(this, new InputIdentitasPemesanActivityViewModelFactory())
                    .get(InputIdentitasPemesanActivityViewModel.class);
        }

        /*MENGAMBIL DATA PESANAN DARI VIEW MODEL MAINACTIVITY*/
        PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
        Log.d("PEMESANAN DATA", pemesananData.getJumlah_penumpang());

        /*MEMASUKKAN DATA PROFILE DATA SEBAGAI PEMESAN*/
        pemesan = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        /*MEMASUKKAN DATA PEMESAN KE TRANSAKSI DATA LIVE DATA*/
        TransaksiData transaksiData = inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
        transaksiData.setId_jadwal(pemesananData.getJadwalEntity().getId());
        transaksiData.setId_user(Long.parseLong(pemesan.getUser_id()));
        transaksiData.setTanggal(pemesananData.getTanggal_variable());
        transaksiData.setTotal_biaya((Long.parseLong(pemesananData.getJadwalEntity().getHarga()) * Long.parseLong(pemesananData.getJumlah_penumpang())));
        inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);

        /*INIT PENUMPANG DATA VIEW MODEL*/
        MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();

        /*OBSERVER*/
        InputIdentitasPemesanActivity
                .inputIdentitasPemesanActivityViewModel
                .getListPenumpangLiveData()
                .observe(this, new Observer<List<PenumpangData>>() {
                    @Override
                    public void onChanged(List<PenumpangData> penumpangData) {
                        InputIdentitasPemesanActivity.this.penumpangAdapter.penumpangData = penumpangData;
                        InputIdentitasPemesanActivity.this.penumpangAdapter.notifyDataSetChanged();
                    }
                });
    }

    /*INIT SEMUA WIDGET PEMESANAN*/
    private void initWidget() {
        this.tvasaltujuan = findViewById(R.id.pelabuhanasaltujuan);
        this.tvdetailjadwal = findViewById(R.id.jadwaldetail);
        this.tvnamapemesan = findViewById(R.id.namapemesan);
        this.tvnomorpemesan = findViewById(R.id.nomorhppemesan);
        this.tvemailpemesan = findViewById(R.id.emailpemesan);
        this.toggle = findViewById(R.id.togglebuttonpemesanadalahpenumpang);
        this.tvtotalbiaya = findViewById(R.id.totalbiaya);
        this.btnlanjutkan = findViewById(R.id.btnlanjutkanpembayaran);
        this.loading = findViewById(R.id.loadinglayout);

        /*WIDGET UTIL*/

        /*SET DETAIL JADWAL*/
        PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
        String asaltujuan = pemesananData.getAsal() + " > " + pemesananData.getTujuan();
        String detai_jadwal = pemesananData.getJadwalEntity().getPelabuhan_asal_kode() +
                                " >> " + pemesananData.getJadwalEntity().getPelabuhan_tujuan_kode() +
                                " [ " + pemesananData.getTanggal() + " ] " +
                                pemesananData.getJadwalEntity().getWaktu_berangkat();

        Log.d("alin_pelabuhan", pemesananData.getAsal());
        this.tvasaltujuan.setText(asaltujuan);
        this.tvdetailjadwal.setText(detai_jadwal);

        /*SET PROFILE PENGGUNA*/
        this.tvnamapemesan.setText(this.pemesan.getName());
        this.tvemailpemesan.setText(this.pemesan.getEmail());
        this.tvnomorpemesan.setText(this.pemesan.getNohp());

        /*INIT TOGGLE*/
        this.toggle.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    if(on){
                        InputIdentitasPemesanActivity.this.toggle.setToggleOff();
                    }else{
                        InputIdentitasPemesanActivity.this.toggle.setToggleOn();
                    }
                    return ;
                }
                else{
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if(on){
                        showBottomSheet(pemesan.getName(),"",0);
                    }else{
                        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
                        penumpangDataList.get(0).setNama_pemegang_ticket("");
                        penumpangDataList.get(0).setNo_id_card("");
                        InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangDataList);
                    }
                }

            }
        });

        /*TOTAL BIAYA*/
        TransaksiData transaksiData = inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
        long total_biaya = transaksiData.getTotal_biaya();

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String total_biaya_rupiah = "IDR " + kursIndonesia.format(total_biaya);
        this.tvtotalbiaya.setText(total_biaya_rupiah);

        /*SET BUTTON PEMBAYARAN EVENT LISTENER*/
        this.btnlanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doValidate()){
                    /*PREPARE POST DATA*/
                    String id_pemesan = pemesan.getUser_id();
                    String id_jadwal = String.valueOf(InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue().getId_jadwal());
                    String token = pemesan.getToken();

                    /*UBAH POJO MENJADI JSON*/
                    Gson gson = new Gson();
                    String penumpangjson = gson.toJson(InputIdentitasPemesanActivity.this.penumpangAdapter.penumpangData);

                    showModalPersetujuanPemesanan(token, id_pemesan, id_jadwal,penumpangjson);
                }
            }
        });
    }

    /*FILL RECYCLERVIEW DENGAN DATA*/
    private void fillRecyclerView(){
        /*PRE FILL*/
        this.recyclerView = findViewById(R.id.rvInputIdentitas);
        this.layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.recyclerView.setLayoutManager(this.layoutManager);
        /*AMBIL JUMLAH PENUMPANG*/
        PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();

        /*AMBIL VIEW MODEL PENUMPANG*/
        List<PenumpangData> penumpangData = inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

        int jumlah_penumpang = Integer.parseInt(pemesananData.getJumlah_penumpang());

        if(jumlah_penumpang != penumpangData.size()) {
            for (int i = 0; i < jumlah_penumpang; i++) {
                PenumpangData penumpang = new PenumpangData();
                penumpang.setHarga(Long.parseLong(pemesananData.getJadwalEntity().getHarga()));
                penumpangData.add(penumpang);
            }
        }

        /*TOOGLE FIXING*/
        if(!penumpangData.get(0).getNama_pemegang_ticket().matches("")){
            toggle.setToggleOn();
        }

        /*MASUKKAN PENUMPANG DATA KE DALAM VIEWMODEL*/
        inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangData);

        this.penumpangAdapter = new PenumpangAdapter(this,penumpangData);
        this.recyclerView.setAdapter(this.penumpangAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
    }

    /*METHOD YANG DIGUNAKAN UNTUK MENAMPILKAN BOTTOMSHEET PENUMPANG*/
    public void showBottomSheet(String nama, String nomor_identitas, int index){
        BottomSheetPenumpangFragment bottomSheetJumlahPenumpang = new BottomSheetPenumpangFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(INDEX_PENUMPANG, index);
        bundle.putString(NAMA_PENUMPANG, nama);
        bundle.putString(NOMOR_IDENTITAS,nomor_identitas);
        bottomSheetJumlahPenumpang.setArguments(bundle);

        bottomSheetJumlahPenumpang.showNow(getSupportFragmentManager(),"TAG");
    }

    /*DIPANGGIL SAAT ACTIVITY DIHANCURKAN*/
    @Override
    protected void onDestroy() {
        /*RESET PENUMPANG PADA VIEW MODEL*/
        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
        penumpangDataList.clear();
        InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangDataList);

        super.onDestroy();
    }

    /*VALIDASI SEMUA INPUT REQUEST KE SERVER*/
    private boolean doValidate() {
        final int[] validation = {1};

        List<PenumpangData> penumpangDataList = this.penumpangAdapter.penumpangData;

        penumpangDataList.forEach(new Consumer<PenumpangData>() {
            @Override
            public void accept(PenumpangData penumpangData) {
                if(penumpangData.getNama_pemegang_ticket().matches("")){
                    validation[0] -=1;
                }

                if(penumpangData.getNo_id_card().matches("")){
                    validation[0] -=1;
                }

                if(penumpangData.getType_id_card().matches("")){
                    validation[0] -=1;
                }
            }
        });

        return validation[0] == 1;
    }

    /*METHOD UNTUK MODAL PERSETUJUAN PENGIRIMAN TIKET*/
    private void showModalPersetujuanPemesanan(String token, String id_pemesan, String id_jadwal, String penumpangjson){
        /*PEMBUATAN KALIMAT SAMBUTAN*/

        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("LAKUKAN PEMESANAN ?")
                .setMessage("Pesanan akan dibuat dan sebuah ticket akan dibooking untuk anda, mohon segera melakukan pembayaran setelahnya")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        /*MEMANGGIL POST PEMSANAN JADWAL API*/
                        Intent intent = new Intent(InputIdentitasPemesanActivity.this, MetodePembayaranActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("BACK", new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    /*METHOD YANG DIPANGGIL UNTUK MEMUNCULKAN LAYOUT LOADING*/
    private void showLoadingLayout(boolean status){
        if(status){
            this.loading.setVisibility(View.VISIBLE);
        }else{
            this.loading.setVisibility(View.INVISIBLE);
        }
    }
}