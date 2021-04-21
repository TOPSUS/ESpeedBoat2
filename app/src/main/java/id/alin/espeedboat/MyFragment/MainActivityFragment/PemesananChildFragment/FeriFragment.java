package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.PemesananJadwalSpeedboatActivity;
import id.alin.espeedboat.R;

public class FeriFragment extends Fragment implements LifecycleOwner {
    // WIDGET HALAMAN FERI
    private MaterialEditText metasal, mettujuan, mettanggal, metjasa, metgolongan, metjumlahpenumpang,
                                metnomorkendaraan;
    private Button btncari;

    // TAG FRAGMENT
    private static final String TAG_FULLSCREEN_FRAGMENT = "TAG_FULLSCREEN_FRAGMENT";
    private static final String TAG_TIPE_JASA = "TAG_TIPE_JASA";
    private static final String TAG_GOLONGAN = "TAG_GOLONGAN";
    private static final String TAG_NOMOR_KENDARAAN = "TAG_NOMOR_KENDARAAN";

    // PUBLIC STATIC UNTUK MENENTUKAN TIPE DARI KAPAL
    public static final String TIPE_KAPAL = "TIPE_KAPAL";
    public static final String FORM = "FORM";
    public static final String ASAL = "ASAL";
    public static final String TUJUAN = "TUJUAN";
    public static final String FERI = "feri";

    // PUBLIC STATIC TIPE JASA
    public static final String PENUMPANG = "Penumpang";
    public static final String KENDARAAN = "Kendaraan";


    // CONSTRUCTOR FRAGMENT HARUS KOSONG
    public FeriFragment(){
        // HARUS KOSONG
    }

    public static FeriFragment newInstance() {

        Bundle args = new Bundle();

        FeriFragment fragment = new FeriFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feri,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidget();
        initViewModel();
    }

    // METHOD UNTUK INISIASI WIDGET PADA HALAMAN ANDROID
    private void initWidget() {
        this.metasal = getView().findViewById(R.id.metPemesananJadwalFragmentDari);
        this.mettujuan = getView().findViewById(R.id.metPemesananJadwalFragmentTujuan);
        this.mettanggal = getView().findViewById(R.id.metPemesananJadwalFragmentTanggal);
        this.metjasa = getView().findViewById(R.id.metJasaPenggunaanFeri);
        this.metgolongan = getView().findViewById(R.id.metGolonganKendaraan);
        this.metnomorkendaraan = getView().findViewById(R.id.metPemesananJadwalFragmentNomorKendaraan);

        this.metjumlahpenumpang = getView().findViewById(R.id.metPemesananJadwalFragmentJumlahpenumpang);
        this.btncari = getView().findViewById(R.id.btncari);

        // MEMASANGKAN LOGIC LISTENER UNTUK SETIAP VIEW

        this.metasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenDialogFeri fullscreenDialogFeri = new FullscreenDialogFeri();
                Bundle bundle = new Bundle();
                bundle.putString(TIPE_KAPAL,FERI);
                bundle.putString(FORM,ASAL);
                fullscreenDialogFeri.setArguments(bundle);
                fullscreenDialogFeri.showNow(getChildFragmentManager(), TAG_FULLSCREEN_FRAGMENT);
            }
        });

        this.mettujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullscreenDialogFeri fullscreenDialogFeri = new FullscreenDialogFeri();
                Bundle bundle = new Bundle();
                bundle.putString(TIPE_KAPAL,FERI);
                bundle.putString(FORM,TUJUAN);
                fullscreenDialogFeri.setArguments(bundle);
                fullscreenDialogFeri.showNow(getChildFragmentManager(), TAG_FULLSCREEN_FRAGMENT);
            }
        });

        this.mettanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDialog calendarDialog = new CalendarDialog(getContext());
                calendarDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        ((CalendarDialog) dialog).setSelectedDayBackgroundColor( Color.parseColor("#4287f5"));
                        ((CalendarDialog) dialog).setSelectionType(SelectionType.SINGLE);
                        ((CalendarDialog) dialog).setSelectionBarMonthTextColor( Color.parseColor("#4287f5"));
                        ((CalendarDialog) dialog).setWeekDayTitleTextColor(Color.parseColor("#4287f5"));
                        ((CalendarDialog) dialog).setWeekendDayTextColor(Color.parseColor("#d9512b"));
                    }
                });
                calendarDialog.setOnDaysSelectionListener(new OnDaysSelectionListener() {
                    @Override
                    public void onDaysSelected(List<Day> selectedDays) {
                        if(selectedDays.size() > 0){
                            StringBuilder date = new StringBuilder();
                            date.append(selectedDays.get(0).getCalendar().get(Calendar.DAY_OF_MONTH));
                            date.append(" ");

                            /*FUNGSI UBAH NAMA BULAN*/
                            String bulan_indonesia = SpeedBoatFragment.transformToBulanIndonesia(selectedDays.get(0).getCalendar().get(Calendar.MONTH));
                            date.append(bulan_indonesia);

                            date.append(" ");
                            date.append(selectedDays.get(0).getCalendar().get(Calendar.YEAR));

                            StringBuilder tanggal_variable = new StringBuilder();
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.YEAR));
                            tanggal_variable.append("-");
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.MONTH)+1);
                            tanggal_variable.append("-");
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.DAY_OF_MONTH));

                            PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                            pemesananFeriData.setTanggal(date.toString());
                            pemesananFeriData.setTanggal_variable(tanggal_variable.toString());
                            Toast.makeText(getContext(), tanggal_variable.toString(), Toast.LENGTH_SHORT).show();
                            MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                        }
                    }
                });
                calendarDialog.show();
            }
        });

        this.metjasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JasaDialogFragment jasaDialogFragment = new JasaDialogFragment();
                jasaDialogFragment.showNow(getChildFragmentManager(),TAG_TIPE_JASA);
            }
        });

        this.metgolongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doValidateOpenGolongan()){
                    FullscreenGolonganFragment fullscreenGolonganFragment = new FullscreenGolonganFragment();
                    fullscreenGolonganFragment.showNow(getChildFragmentManager(),TAG_GOLONGAN);
                }
            }
        });

        this.metjumlahpenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetJumlahPenumpang bottomSheetJumlahPenumpang = new BottomSheetJumlahPenumpang();
                Bundle bundle = new Bundle();
                bundle.putString(BottomSheetJumlahPenumpang.FORM,BottomSheetJumlahPenumpang.FERI);
                bottomSheetJumlahPenumpang.setArguments(bundle);
                bottomSheetJumlahPenumpang.showNow(getChildFragmentManager(),"TAG");
            }
        });

        this.metnomorkendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomorKendaraanFragment nomorKendaraanFragment = new NomorKendaraanFragment();
                nomorKendaraanFragment.show(getChildFragmentManager(),TAG_NOMOR_KENDARAAN);
            }
        });

        // EVENT LISTENER BUTTON BERISIKAN EXTRAS STRING TIPE KAPAL FERI
        this.btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doValiadateCariFeri()){
                    Intent intent = new Intent(getContext(), PemesananJadwalSpeedboatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(PemesananJadwalSpeedboatActivity.TIPE_KAPAL,PemesananJadwalSpeedboatActivity.FERI);
                    startActivity(intent);
                }
            }
        });
    }

    // VIEW MODEL UNTUK FERI FRAGMENT
    private void initViewModel() {
        // INIT VIEW MODEL PERTAMA KALI
        MainActivity.mainActivityViewModel.setPemesananFeriData(new PemesananFeriData());

        // MEMASANGKAN OBSERVER PADA FRAGMENT INI
        MainActivity.mainActivityViewModel.getPemesananFeriLiveData().observe(this, new Observer<PemesananFeriData>() {
            @Override
            public void onChanged(PemesananFeriData pemesananFeriData) {
                metasal.setText(pemesananFeriData.getAsal());
                mettujuan.setText(pemesananFeriData.getTujuan());
                metgolongan.setText(pemesananFeriData.getGologan_kendaraan());
                metnomorkendaraan.setText(pemesananFeriData.getNomor_kendaraan());

                metjumlahpenumpang.setText(
                        (pemesananFeriData.getJumlah_penumpang() == 0) ? "" : String.valueOf(pemesananFeriData.getJumlah_penumpang())
                );
                mettanggal.setText(pemesananFeriData.getTanggal());

                // MENENTUKAN JASA YANG DIGUNAKAN APA BILA KENDARAAN MAKA BUKA FORM GOLONGAN
                if(pemesananFeriData.getTipe_jasa() != null){
                    if(pemesananFeriData.getTipe_jasa().matches(KENDARAAN)){
                        metgolongan.setVisibility(View.VISIBLE);
                        metnomorkendaraan.setVisibility(View.VISIBLE);
                    }else{
                        metgolongan.setVisibility(View.GONE);
                        metnomorkendaraan.setVisibility(View.GONE);
                    }
                    metjasa.setText(pemesananFeriData.getTipe_jasa());
                }
                Log.d("apekaden","perulangan");
            }
        });
    }

    // CHECK DULU APAKAH ASAL PELABUHAN SUDAH TERISI
    private boolean doValidateOpenGolongan(){
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
        if(pemesananFeriData.getAsal().matches("")){
            this.metasal.setError("Mohon tentukan asal pelabuhan terlebih dahulu");
            return false;
        }else{
            return true;
        }

    }

    // VALIDASI TOMBOL CARE FERI
    private boolean doValiadateCariFeri(){
        int validastion = 1;

        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();

        if(pemesananFeriData.getAsal().matches("")){
            validastion -= 1;
            this.metasal.setError("Tentukan asal pelabuhan");
        }

        if(pemesananFeriData.getTujuan().matches("")){
            validastion -= 1;
            this.mettujuan.setError("Tentukan tujuan pelabuhan");
        }

        if(pemesananFeriData.getTanggal().matches("")){
            validastion-=1;
            this.mettanggal.setError("Tentukan tanggal keberangkatan");
        }

        if(pemesananFeriData.getTipe_jasa().matches("")){
            validastion-=1;
            this.metjasa.setError("Tentukan tipe jasa");
        }else if(pemesananFeriData.getTipe_jasa().matches(KENDARAAN)){
            if(pemesananFeriData.getGologan_kendaraan().matches("")){
                validastion -=1;
                this.metgolongan.setError("Tentukan golongan kendaraan");
            }

            if(pemesananFeriData.getNomor_kendaraan().matches("")){
                validastion -=1;
                this.metnomorkendaraan.setError("Masukkan nomor kendaraan");
            }
        }

        if(pemesananFeriData.getJumlah_penumpang() == 0){
            validastion-=1;
            this.metjumlahpenumpang.setError("Tentukan jumlah penumpang");
        }

        return validastion == 1;
    }
}
