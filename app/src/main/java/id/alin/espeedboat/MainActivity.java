package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MyFragment.MainActivityFragment.HomeFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FeriFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananJadwalFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.ProfileFragment;
import id.alin.espeedboat.MyProfile.EditProfileActivity;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityInstanceFactory;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityViewModel;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;

public class MainActivity extends AppCompatActivity {

    /*BOTTOM NAVIGATION NAV*/
    private BottomNavigationView bottomNavigationView;

    // TOP THREEDOT ICON AND TOPSOURCE FRAGMENT
    private ImageButton threedot;
    private DialogPlus dialogPlus;

    private static final String IKUTI_KAMI_LINK = "https://it.unud.ac.id/";

    /*THE FOUR FRAGMENT*/
    private HomeFragment homeFragment;
    private PemesananJadwalFragment pemesananFragment;
    private NotificationFragment NotificationFragment;
    private ProfileFragment profileFragment;

    /*SHARED PREF*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /*VIEW MODEL VARIABLE*/
    public static  MainActivityViewModel mainActivityViewModel;

    /*STATIC VARIABLE*/
    private static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
    private static final String PEMESASNAN_FRAGMENT_TAG = "PEMESASNAN_FRAGMENT_TAG";
    private static final String NOTIFIKASI_FRAGMENT_TAG = "NOTIFIKASI_FRAGMENT_TAG";
    private static final String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*INIT VIEW MODEL MAIN ACTIVITY*/
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        /*SAAT KONFIGURASI APLIKASI BERUBAH*/
        if(null != savedInstanceState) {
            initWidget();
            fragmentAfterFiller();

        /*SAAT TIDAK ADA YANG BERUBAH (AWAL DIBUKA)*/
        }else{
            initViewModel();
            initWidget();
            fragmentFirstTimeFiller();
            Home();
        }
    }

    /*INIT VIEW MODEL YANG AKAN DIGUNAKAN OLEH KE-4 FRAGMENT*/
    private void initViewModel() {
        mainActivityViewModel = new ViewModelProvider(this,new MainActivityInstanceFactory())
                .get(MainActivityViewModel.class);

        ProfileData profileData = new ProfileData(
                sharedPreferences.getString(Config.USER_ID,""),
                sharedPreferences.getString(Config.USER_TOKEN,""),
                sharedPreferences.getString(Config.USER_NAMA,""),
                sharedPreferences.getString(Config.USER_ALAMAT,""),
                sharedPreferences.getString(Config.USER_CHAT_ID,""),
                sharedPreferences.getString(Config.USER_PIN,""),
                sharedPreferences.getString(Config.USER_EMAIL,""),
                sharedPreferences.getString(Config.USER_FOTO,""),
                sharedPreferences.getString(Config.USER_NOHP,""),
                sharedPreferences.getString(Config.USER_JENIS_KELAMIN,""),
                sharedPreferences.getString(Config.USER_FCM_TOKEN,"")
        );
        Log.d("apekaden_2",sharedPreferences.getString(Config.USER_FCM_TOKEN,""));
        mainActivityViewModel.setProfileData(profileData);
    }

    /*INIT WIDGET HALAMAN MAIN ACTIVITY (SEARCH VIEW DAN BOTNAV)*/
    private void initWidget(){

        /*INIT BOTTOM NAV*/
        bottomNavigationView = findViewById(R.id.botnavbarMainActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.itemmenuHome){
                    /*HOME FRAGMENT TEST APAKAH AKTIF ATAU TIDAK KALAU AKTIF TIDAK DIGANTI*/
                    item.setChecked(true);
                    Home();
                }
                else if(item.getItemId() == R.id.itemmenuPemesanan){
                    item.setChecked(true);
                    Pemesanan();
                }
                else if(item.getItemId() == R.id.itemmenuNotifikasi){
                    item.setChecked(true);
                    Notification();
                }else if(item.getItemId() == R.id.itemmenuProfile){
                    item.setChecked(true);
                    Profile();
                }

                return false;
            }
        });

        // THREEDOT INIT
        this.threedot = findViewById(R.id.threedot);
        this.threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.this.dialogPlus == null){
                    MainActivity.this.dialogPlus = DialogPlus.newDialog(MainActivity.this)
                            .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                            .setGravity(Gravity.TOP)
                            .setContentHolder(new ViewHolder(R.layout.topsheet_appdetail_fragment))
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(DialogPlus dialog, View view) {
                                    if(view.getId() == R.id.closebutton){
                                        dialog.dismiss();
                                    }
                                    else if(view.getId() == R.id.ikutikami){
                                        Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                                        intent.putExtra(WebviewActivity.LINK,IKUTI_KAMI_LINK);
                                        startActivity(intent);
                                    }else if(view.getId() == R.id.pengaturan){
                                        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                                        startActivity(intent);
                                }
                            }})
                            .setOnDismissListener(new OnDismissListener() {
                                @Override
                                public void onDismiss(DialogPlus dialog) {
                                    MainActivity.this.dialogPlus = null;
                                }
                            })
                            .create();
                    MainActivity.this.dialogPlus.show();
                }
            }
        });
    }

    /*MEMASUKKAN SEMUA FRAGMENT KE DALAM FRAMELAYOUT*/
    private void fragmentFirstTimeFiller() {
        /*FRAGMENT PROFILE*/
        if(this.profileFragment == null){
            profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,profileFragment,PROFILE_FRAGMENT_TAG).commit();
        }

        /*FRAGMENT NOTIFICATION*/
        if(this.NotificationFragment == null){
            NotificationFragment = new NotificationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout, NotificationFragment,NOTIFIKASI_FRAGMENT_TAG).commit();
        }

        /*FRAGMENT PEMESANAN*/
        if(this.pemesananFragment == null){
            pemesananFragment = new PemesananJadwalFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,pemesananFragment,PEMESASNAN_FRAGMENT_TAG).commit();
        }

        /*FRAGMENT HOME*/
        if(this.homeFragment == null){
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,homeFragment,HOME_FRAGMENT_TAG).commit();
        }

    }

    /*
    *
    * METHOD YANG DIGUNAKAN SAAT ACTIVITY BERUBAH CONFIGURASINYA
    * BAIK SAAT ORIENTASI LAYAR
    * ATAU
    * PERUBAHAN MODE TERANG / GELAP
    *
    * */
    private void fragmentAfterFiller(){
        Fragment home = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);

        if(home != null){
            this.homeFragment = (HomeFragment) home;
            Log.d("KOSONG","KOSONG 1");
            if(this.homeFragment.isVisible()){
                Home();
            }
        }

        Fragment pemesanan = getSupportFragmentManager().findFragmentByTag(PEMESASNAN_FRAGMENT_TAG);

        if(pemesanan != null){
            this.pemesananFragment = (PemesananJadwalFragment) pemesanan;
            Log.d("KOSONG","KOSONG 2");
            if(this.pemesananFragment.isVisible()){
                Pemesanan();
            }
        }

        Fragment notifikasi = getSupportFragmentManager().findFragmentByTag(NOTIFIKASI_FRAGMENT_TAG);

        if(notifikasi != null){
            this.NotificationFragment = (NotificationFragment) notifikasi;
            if(this.NotificationFragment.isVisible()){
                Notification();
            }
        }

        Fragment profile = getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG);

        if(profile != null){
            this.profileFragment = (ProfileFragment) profile;
            if(this.profileFragment.isVisible()){
                Profile();
            }
        }
    }

    /*
    * METHOD UNTUK MEMANGGIL FRAGMENT HOME
    * */
    private void Home(){
        if(homeFragment != null){
            Log.d("TEST","TEST 1");
            getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
        }
        else if(!homeFragment.isVisible()){
            Log.d("TEST","TEST 2");
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,homeFragment,HOME_FRAGMENT_TAG).commit();
            getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
        }


        getSupportFragmentManager().beginTransaction().hide(pemesananFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(NotificationFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();

    }

    /*
    * METHOD MEMANGGIL FRAGMENT PEMESANAN
    * */
    private void Pemesanan(){
        Log.d("TEST","TEST PEMESANAN");

        if(pemesananFragment != null){
            Log.d("TEST","TEST 3");
            getSupportFragmentManager().beginTransaction().show(pemesananFragment).commit();
        }
        else if(!pemesananFragment.isVisible()){
            Log.d("TEST","TEST 4");
            pemesananFragment = new PemesananJadwalFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,pemesananFragment,PEMESASNAN_FRAGMENT_TAG).commit();
            getSupportFragmentManager().beginTransaction().show(pemesananFragment).commit();
        }


        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(NotificationFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();
    }

    /*
    * METHOD MEMANGGIL FRAGMENT NOTIFICATION
    * */
    private void Notification(){
        if(NotificationFragment != null){
            Log.d("TEST","TEST 5");
            getSupportFragmentManager().beginTransaction().show(NotificationFragment).commit();
        }
        else if(!NotificationFragment.isVisible()){
            Log.d("TEST","TEST 6");
            NotificationFragment = new NotificationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout, NotificationFragment,NOTIFIKASI_FRAGMENT_TAG).commit();
            getSupportFragmentManager().beginTransaction().show(pemesananFragment).commit();
        }

        getSupportFragmentManager().beginTransaction().hide(pemesananFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();
    }

    /*
    * METHOD MEMANGGIL FRAGMENT PROFILE
    * */
    private void Profile(){

        if(profileFragment != null){
            getSupportFragmentManager().beginTransaction().show(profileFragment).commit();
        }
        else if(!profileFragment.isVisible()){
            profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,profileFragment,PROFILE_FRAGMENT_TAG).commit();
            getSupportFragmentManager().beginTransaction().show(pemesananFragment).commit();
        }

        getSupportFragmentManager().beginTransaction().hide(pemesananFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(NotificationFragment).commit();
    }

    @Override
    public void onBackPressed() {
        /*INIT MODAL*/
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Keluar dari ESpeedBoat ?")
                .setCancelable(false)
                .setAnimation(R.raw.animation_boat_2)
                .setPositiveButton("OKE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        MainActivity.super.onBackPressed();
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
}