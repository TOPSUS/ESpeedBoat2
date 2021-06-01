package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.zcw.togglebutton.ToggleButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyAdapter.PenumpangAdapter;
import id.alin.espeedboat.MyFragment.InputIdentitasPemesanActivityFragment.BottomSheetPenumpangFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FeriFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenGolonganFragment;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModel;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.InputIdentitasPemesanActivityViewModelFactory;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;

public class InputIdentitasPemesanActivity extends AppCompatActivity implements LifecycleOwner {
    private RecyclerView recyclerView;
    private PenumpangAdapter penumpangAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static InputIdentitasPemesanActivityViewModel inputIdentitasPemesanActivityViewModel;

    /*WIDGET*/
    private TextView tvasaltujuan, tvdetailjadwal, tvnamapemesan, tvemailpemesan, tvnomorpemesan,
            tvtotalbiaya, tvgolongan, tvhargagolongan, tvketerangangolongan;
    private Button btnlanjutkan;
    private ImageButton backbutton;
    private LinearLayout loading, layout_feri, layout_penumpang;

    public ToggleButton toggle;

    /*PEMESAN DIAMBIL DARI PROFILEDATA MAINACTIVITY*/
    private ProfileData pemesan;

    /*PUBLIC STATIC KEY*/
    public static final String INDEX_PENUMPANG = "INDEX_PENUMPANG";
    public static final String NAMA_PENUMPANG = "NAMA_PENUMPANG";
    public static final String NOMOR_IDENTITAS = "NOMOR_IDENTITAS";

    /*BUTTON ANTI DOUBLE CLICK*/
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_identitas_pemesan);

        if (getIntent().getStringExtra(PemesananJadwalSpeedboatActivity.TIPE_KAPAL).matches(PemesananJadwalSpeedboatActivity.SPEEDBOAT)) {
            initViewModelSpeedboat();
            initWidgetSpeedboat();
            fillRecyclerViewSpeedboat();
        } else {
            initViewModelFeri();
            initWidgetFeri();
            fillRecyclerViewFeri();
        }
    }

    // MEMBENTUK VIEW MODEL
    private void initViewModelSpeedboat() {
        // MEMBUYAT VIEW MODEL*/
        if (inputIdentitasPemesanActivityViewModel == null) {
            inputIdentitasPemesanActivityViewModel = new ViewModelProvider(this, new InputIdentitasPemesanActivityViewModelFactory())
                    .get(InputIdentitasPemesanActivityViewModel.class);
        }

        // MENGAMBIL DATA PESANAN DARI VIEW MODEL MAINACTIVITY*/
        PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
        Log.d("PEMESANAN DATA", pemesananSpeedboatData.getJumlah_penumpang());

        // MEMASUKKAN DATA PROFILE DATA SEBAGAI PEMESAN*/
        pemesan = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        // MEMASUKKAN DATA PEMESAN KE TRANSAKSI DATA LIVE DATA*/
        TransaksiData transaksiData = inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
        transaksiData.setId_jadwal(pemesananSpeedboatData.getJadwalEntity().getId());
        transaksiData.setId_user(Long.parseLong(pemesan.getUser_id()));
        transaksiData.setTanggal(pemesananSpeedboatData.getTanggal_variable());
        transaksiData.setTotal_biaya((Long.parseLong(pemesananSpeedboatData.getJadwalEntity().getHarga()) * Long.parseLong(pemesananSpeedboatData.getJumlah_penumpang())));
        inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);

        // INIT PENUMPANG DATA VIEW MODEL
        MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();

        // OBSERVER
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

    // INIT SEMUA WIDGET APABILA SPEEDBOAT
    private void initWidgetSpeedboat() {
        this.tvasaltujuan = findViewById(R.id.pelabuhanasaltujuan);
        this.tvdetailjadwal = findViewById(R.id.jadwaldetail);
        this.tvnamapemesan = findViewById(R.id.namapemesan);
        this.tvnomorpemesan = findViewById(R.id.nomorhppemesan);
        this.tvemailpemesan = findViewById(R.id.emailpemesan);
        this.toggle = findViewById(R.id.togglebuttonpemesanadalahpenumpang);
        this.tvtotalbiaya = findViewById(R.id.totalbiaya);
        this.btnlanjutkan = findViewById(R.id.btnlanjutkanpembayaran);
        this.loading = findViewById(R.id.loadinglayout);
        this.layout_penumpang = findViewById(R.id.penumpanglayout);

        this.layout_penumpang.setVisibility(View.VISIBLE);
        this.backbutton = findViewById(R.id.backButton);
        /*WIDGET UTIL*/

        /*SET DETAIL JADWAL*/
        PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
        String asaltujuan = pemesananSpeedboatData.getAsal() + " > " + pemesananSpeedboatData.getTujuan();
        String detai_jadwal = pemesananSpeedboatData.getJadwalEntity().getPelabuhan_asal_kode() +
                " >> " + pemesananSpeedboatData.getJadwalEntity().getPelabuhan_tujuan_kode() +
                " [ " + pemesananSpeedboatData.getTanggal() + " ] " +
                pemesananSpeedboatData.getJadwalEntity().getWaktu_berangkat();

        Log.d("alin_pelabuhan", pemesananSpeedboatData.getAsal());
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
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    if (on) {
                        InputIdentitasPemesanActivity.this.toggle.setToggleOff();
                    } else {
                        InputIdentitasPemesanActivity.this.toggle.setToggleOn();
                    }
                    return;
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (on) {
                        showBottomSheet(pemesan.getName(), "", 0);
                    } else {
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

        // SET BUTTON PEMBAYARAN EVENT LISTENER*/
        this.btnlanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidate()) {
                    showModalPersetujuanPemesanan();
                }else{
                    Toast.makeText(InputIdentitasPemesanActivity.this, "MOHON INPUTKAN SEMUA DATA PENUMPANG", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // SET BACK BUTTON EVENT LISTENER
        this.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // FILL RECYCLERVIEW DENGAN DATA
    private void fillRecyclerViewSpeedboat() {
        // PRE FILL
        this.recyclerView = findViewById(R.id.rvInputIdentitas);
        this.layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.recyclerView.setLayoutManager(this.layoutManager);

        // AMBIL JUMLAH PENUMPANG
        PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();

        // AMBIL VIEW MODEL PENUMPANG
        List<PenumpangData> penumpangData = inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

        int jumlah_penumpang = Integer.parseInt(pemesananSpeedboatData.getJumlah_penumpang());

        if (jumlah_penumpang != penumpangData.size()) {
            for (int i = 0; i < jumlah_penumpang; i++) {
                PenumpangData penumpang = new PenumpangData();
                penumpang.setHarga(Long.parseLong(pemesananSpeedboatData.getJadwalEntity().getHarga()));
                penumpangData.add(penumpang);
            }
        }

        // TOOGLE FIXING*/
        if (!penumpangData.get(0).getNama_pemegang_ticket().matches("")) {
            toggle.setToggleOn();
        }

        // MASUKKAN PENUMPANG DATA KE DALAM VIEWMODEL*/
        inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangData);

        this.penumpangAdapter = new PenumpangAdapter(this, penumpangData);
        this.recyclerView.setAdapter(this.penumpangAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
    }

    // MEMBENTUK VIEW MODEL
    private void initViewModelFeri() {
        // MEMBUYAT VIEW MODEL*/
        if (inputIdentitasPemesanActivityViewModel == null) {
            inputIdentitasPemesanActivityViewModel = new ViewModelProvider(this, new InputIdentitasPemesanActivityViewModelFactory())
                    .get(InputIdentitasPemesanActivityViewModel.class);
        }

        // MENGAMBIL DATA PESANAN DARI VIEW MODEL MAINACTIVITY*/
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();

        // MEMASUKKAN DATA PROFILE DATA SEBAGAI PEMESAN*/
        pemesan = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        // MEMASUKKAN DATA PEMESAN KE TRANSAKSI DATA LIVE DATA*/
        TransaksiData transaksiData = inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
        transaksiData.setId_jadwal(pemesananFeriData.getJadwalEntity().getId());
        transaksiData.setId_user(Long.parseLong(pemesan.getUser_id()));
        transaksiData.setTanggal(pemesananFeriData.getTanggal_variable());

        // MENTUKAN HARGA BERDASARKAN GOLONGAN KENDARAAN APABILA ADA
        if (pemesananFeriData.getTipe_jasa().matches(FeriFragment.KENDARAAN)) {
            int total_harga = 0;

            // TOTAL DITAMBAH DENGAN INCLUDE DARI GOLONGAN TERLEBIH DAHULU
            total_harga += pemesananFeriData.getHarga();
            Log.d("apakaden", String.valueOf(pemesananFeriData.getHarga()));
            // TAMBAHKAN DENGAN SISA PENUMPANG
//            total_harga += ((pemesananFeriData.getJumlah_penumpang() - 1) * Integer.parseInt(pemesananFeriData.getJadwalEntity().getHarga()));
            transaksiData.setTotal_biaya(total_harga);
            inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);
        } else {
            int total_harga = 0;

            total_harga += pemesananFeriData.getJumlah_penumpang() * Integer.parseInt(pemesananFeriData.getJadwalEntity().getHarga());
            transaksiData.setTotal_biaya((total_harga));
            inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);
        }

        // INIT PENUMPANG DATA VIEW MODEL
        MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();

        // OBSERVER
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

    // INI UNTUK WIDGET FERI
    private void initWidgetFeri() {
        this.tvasaltujuan = findViewById(R.id.pelabuhanasaltujuan);
        this.tvdetailjadwal = findViewById(R.id.jadwaldetail);
        this.tvnamapemesan = findViewById(R.id.namapemesan);
        this.tvnomorpemesan = findViewById(R.id.nomorhppemesan);
        this.tvemailpemesan = findViewById(R.id.emailpemesan);
        this.toggle = findViewById(R.id.togglebuttonpemesanadalahpenumpang);
        this.tvtotalbiaya = findViewById(R.id.totalbiaya);
        this.btnlanjutkan = findViewById(R.id.btnlanjutkanpembayaran);
        this.loading = findViewById(R.id.loadinglayout);
        this.layout_penumpang = findViewById(R.id.penumpanglayout);
        this.layout_penumpang.setVisibility(View.VISIBLE);
        this.layout_feri = findViewById(R.id.ferilayout);
        this.tvgolongan = findViewById(R.id.golongan);
        this.tvketerangangolongan = findViewById(R.id.keterangan_golongan);
        this.tvhargagolongan = findViewById(R.id.harga_golongan);
        this.backbutton = findViewById(R.id.backButton);

        /*SET DETAIL JADWAL*/
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
        String asaltujuan = pemesananFeriData.getAsal() + " > " + pemesananFeriData.getTujuan();
        String detai_jadwal = pemesananFeriData.getJadwalEntity().getPelabuhan_asal_kode() +
                " >> " + pemesananFeriData.getJadwalEntity().getPelabuhan_tujuan_kode() +
                " [ " + pemesananFeriData.getTanggal() + " ] " +
                pemesananFeriData.getJadwalEntity().getWaktu_berangkat();

        Log.d("alin_pelabuhan", pemesananFeriData.getAsal());
        this.tvasaltujuan.setText(asaltujuan);
        this.tvdetailjadwal.setText(detai_jadwal);

        /*SET PROFILE PENGGUNA*/
        this.tvnamapemesan.setText(this.pemesan.getName());
        this.tvemailpemesan.setText(this.pemesan.getEmail());
        this.tvnomorpemesan.setText(this.pemesan.getNohp());

        // PERLIHATKAN GOLONGAN APABILA TIPE JASA ITU KENDARAAN
        if (MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue().getTipe_jasa().matches(FeriFragment.KENDARAAN)) {
            this.layout_feri.setVisibility(View.VISIBLE);
            String kendaraan = pemesananFeriData.getGologan_kendaraan() + " - [ " + pemesananFeriData.getNomor_kendaraan() + " ]";
            this.tvgolongan.setText(kendaraan);
            this.tvgolongan.setAllCaps(true);
            this.tvketerangangolongan.setText(pemesananFeriData.getKeterangan_golongan());
            int total_biaya = pemesananFeriData.getHarga();

            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            String string_biaya_golongan = "IDR " + kursIndonesia.format(total_biaya);

            this.tvhargagolongan.setText(string_biaya_golongan);

        }

        /*INIT TOGGLE*/
        this.toggle.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    if (on) {
                        InputIdentitasPemesanActivity.this.toggle.setToggleOff();
                    } else {
                        InputIdentitasPemesanActivity.this.toggle.setToggleOn();
                    }
                    return;
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (on) {
                        showBottomSheet(pemesan.getName(), "", 0);
                    } else {
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

        // SET BUTTON PEMBAYARAN EVENT LISTENER*/
        this.btnlanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidate()) {
                    showModalPersetujuanPemesanan();
                }else{
                    Toast.makeText(InputIdentitasPemesanActivity.this, "MOHON INPUTKAN SEMUA DATA PENUMPANG", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // SET BACK BUTTON EVENT LISTENER
        this.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // FILL RECYCLERVIEW UNTUK FERI
    private void fillRecyclerViewFeri() {
        // PRE FILL
        this.recyclerView = findViewById(R.id.rvInputIdentitas);
        this.layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.recyclerView.setLayoutManager(this.layoutManager);

        // AMBIL JUMLAH PENUMPANG
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();

        // AMBIL VIEW MODEL PENUMPANG
        List<PenumpangData> penumpangData = inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();

        long jumlah_penumpang = pemesananFeriData.getJumlah_penumpang();

        if (jumlah_penumpang != penumpangData.size()) {
            for (int i = 0; i < jumlah_penumpang; i++) {
                PenumpangData penumpang = new PenumpangData();
                if (pemesananFeriData.getTipe_jasa().matches(FeriFragment.KENDARAAN) && i == 0) {
                    penumpang.setHarga(pemesananFeriData.getHarga());
                    penumpangData.add(penumpang);
                    Log.d("apekaden_1",String.valueOf(pemesananFeriData.getHarga()));
                } else {
                    penumpang.setHarga(Long.parseLong(pemesananFeriData.getJadwalEntity().getHarga()));
                    penumpangData.add(penumpang);
                    Log.d("apekaden_2",String.valueOf(pemesananFeriData.getJadwalEntity().getHarga()));
                }
            }
        }

        Gson gson = new Gson();
        String jsonPenumpang = gson.toJson(penumpangData);
        Log.d("apekaden_3",jsonPenumpang);

        // TOOGLE FIXING*/
        if (!penumpangData.get(0).getNama_pemegang_ticket().matches("")) {
            toggle.setToggleOn();
        }

        // MASUKKAN PENUMPANG DATA KE DALAM VIEWMODEL*/
        inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangData);

        this.penumpangAdapter = new PenumpangAdapter(this, penumpangData);
        this.recyclerView.setAdapter(this.penumpangAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
    }

    // METHOD YANG DIGUNAKAN UNTUK MENAMPILKAN BOTTOMSHEET PENUMPANG
    public void showBottomSheet(String nama, String nomor_identitas, int index) {
        BottomSheetPenumpangFragment bottomSheetJumlahPenumpang = new BottomSheetPenumpangFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(INDEX_PENUMPANG, index);
        bundle.putString(NAMA_PENUMPANG, nama);
        bundle.putString(NOMOR_IDENTITAS, nomor_identitas);
        bottomSheetJumlahPenumpang.setArguments(bundle);

        bottomSheetJumlahPenumpang.showNow(getSupportFragmentManager(), "TAG");
    }

    // DIPANGGIL SAAT ACTIVITY DIHANCURKAN
    @Override
    protected void onDestroy() {
        /*RESET PENUMPANG PADA VIEW MODEL*/
        List<PenumpangData> penumpangDataList = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getListPenumpangLiveData().getValue();
        penumpangDataList.clear();
        InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setListPenumpangLivedata(penumpangDataList);

        super.onDestroy();
    }

    // VALIDASI SEMUA INPUT REQUEST KE SERVER
    private boolean doValidate() {
        final int[] validation = {1};

        List<PenumpangData> penumpangDataList = this.penumpangAdapter.penumpangData;

        penumpangDataList.forEach(new Consumer<PenumpangData>() {
            @Override
            public void accept(PenumpangData penumpangData) {
                if (penumpangData.getNama_pemegang_ticket().matches("")) {
                    validation[0] -= 1;
                }

                if (penumpangData.getNo_id_card().matches("")) {
                    validation[0] -= 1;
                }

                if (penumpangData.getType_id_card().matches("")) {
                    validation[0] -= 1;
                }
            }
        });

        return validation[0] == 1;
    }

    // METHOD UNTUK MODAL PERSETUJUAN PENGIRIMAN TIKET
    private void showModalPersetujuanPemesanan() {
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
                        String tipe_kapal = getIntent().getStringExtra(PemesananJadwalSpeedboatActivity.TIPE_KAPAL);

                        dialogInterface.dismiss();
                        /*MEMANGGIL POST PEMSANAN JADWAL API*/
                        Intent intent = new Intent(InputIdentitasPemesanActivity.this, MetodePembayaranActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra(FeriFragment.TIPE_KAPAL, tipe_kapal);
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

    // METHOD YANG DIPANGGIL UNTUK MEMUNCULKAN LAYOUT LOADING
    private void showLoadingLayout(boolean status) {
        if (status) {
            this.loading.setVisibility(View.VISIBLE);
        } else {
            this.loading.setVisibility(View.INVISIBLE);
        }
    }
}