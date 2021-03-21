package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.BottomSheetJumlahPenumpang;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenDialogAsal;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenDialogTujuan;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.PemesananJadwalActivity;
import id.alin.espeedboat.R;

public class PemesananJadwalFragment extends Fragment implements LifecycleOwner {

    /*WIDGET YANG DIGUNAKAN PADA LAMAN*/
    private MaterialEditText metasal;
    private MaterialEditText mettujuan;
    private MaterialEditText mettanggal;
    private MaterialEditText metjumlahpenumpang;
    private Button btncari;

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

        this.mettanggal = getView().findViewById(R.id.metPemesananJadwalFragmentTanggal);
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
                            String bulan_indonesia = PemesananJadwalFragment.transformToBulanIndonesia(selectedDays.get(0).getCalendar().get(Calendar.MONTH));
                            date.append(bulan_indonesia);

                            date.append(" ");
                            date.append(selectedDays.get(0).getCalendar().get(Calendar.YEAR));

                            StringBuilder tanggal_variable = new StringBuilder();
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.YEAR));
                            tanggal_variable.append("-");
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.MONTH)+1);
                            tanggal_variable.append("-");
                            tanggal_variable.append(selectedDays.get(0).getCalendar().get(Calendar.DAY_OF_MONTH));


                            PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                            pemesananData.setTanggal(date.toString());
                            pemesananData.setTanggal_variable(tanggal_variable.toString());
                            MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
                        }
                    }
                });
                calendarDialog.show();
            }
        });

        this.metjumlahpenumpang = getView().findViewById(R.id.metPemesananJadwalFragmentJumlahpenumpang);
        this.metjumlahpenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetJumlahPenumpang bottomSheetJumlahPenumpang = new BottomSheetJumlahPenumpang();
                bottomSheetJumlahPenumpang.showNow(getChildFragmentManager(),"TAG");
            }
        });

        this.btncari = getView().findViewById(R.id.btncari);
        this.btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doValidateData()){
                    Intent intent = new Intent(getContext(), PemesananJadwalActivity.class);
                    startActivity(intent);
                }
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
                mettanggal.setText(pemesananData.getTanggal());
                metjumlahpenumpang.setText(pemesananData.getJumlah_penumpang());
            }
        });
    }

    /*VALIDASI SAJA JANGAN DILIAT TIDAK PENTING*/
    private boolean doValidateData(){
        int validation = 1;

        PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();

        if(pemesananData.getAsal().matches("")){
            validation -= 1;
            metasal.setError("Mohon tentukan pelabuhan asal");
        }

        if(pemesananData.getTujuan().matches("")){
            validation-=1;
            mettujuan.setError("Mohon tentukan pelabuhan tujuan");
        }

        if(!pemesananData.getAsal().matches("") && !pemesananData.getTujuan().matches("")){
            if(pemesananData.getTujuan().matches(pemesananData.getAsal())){
                validation-=1;
                metasal.setError("Tidak boleh sama dengan tujuan");
                mettujuan.setError("Tidak boleh sama dengan asal");
            }
        }

        if(pemesananData.getTanggal_variable().matches("")){
            validation -= 1;
            mettanggal.setError("Tanggal harus diisi");
        }else {
            LocalDate hari_ini = LocalDate.now();
            LocalDate hari_input = LocalDate.parse(pemesananData.getTanggal_variable(), DateTimeFormatter.ofPattern("yyyy-M-d"));
            if (hari_input.isBefore(hari_ini)) {
                validation -= 1;
                mettanggal.setError("Tanggal telah lewat");
            }
        }

        if(pemesananData.getJumlah_penumpang().matches("")){
            validation -= 1;
            metjumlahpenumpang.setError("Mohon tentukan jumlah penumpang");
        }

        if(validation == 1){
            return true;
        }else{
            return false;
        }

    }

    /*METHOD UNTUK MENGUBAH BULAN ANGKA KE NAMA BULAN DALAM BAHASA INDONESIA*/
    public static String transformToBulanIndonesia(int bulan_angka){
        String bulan;

        if(bulan_angka == 0){
            bulan = "Januari";
        }
        else if(bulan_angka == 1){
            bulan = "Februari";
        }
        else if(bulan_angka == 2){
            bulan = "Maret";
        }
        else if(bulan_angka == 3){
            bulan = "April";
        }
        else if(bulan_angka == 4){
            bulan = "Mei";
        }
        else if(bulan_angka == 5){
           bulan =  "Juni";
        }
        else if(bulan_angka == 6){
            bulan = "Juli";
        }
        else if(bulan_angka == 7){
            bulan = "Agustus";
        }
        else if(bulan_angka == 8){
            bulan = "September";
        }
        else if(bulan_angka == 9){
            bulan = "Oktober";
        }
        else if(bulan_angka == 10){
            bulan = "November";
        }
        else{
            bulan = "Desember";
        }

        return bulan;
    }
}