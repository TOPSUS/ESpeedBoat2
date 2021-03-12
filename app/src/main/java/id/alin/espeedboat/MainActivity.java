package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import id.alin.espeedboat.MyFragment.MainActivityFragment.HomeFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.ProfileFragment;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityInstanceFactory;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.MainActivityViewModel;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ProfileData;

public class MainActivity extends AppCompatActivity {
    /*SEARCHVIEW*/
    private SearchView searchview;

    /*BOTTOM NAVIGATION NAV*/
    private BottomNavigationView bottomNavigationView;

    /*THE FOUR FRAGMENT*/
    private HomeFragment homeFragment;
    private PemesananFragment pemesananFragment;
    private NotificationFragment notificationFragment;
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

        /*NON AKTIFKAN MODE MALAM*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        /*INIT VIEW MODEL MAIN ACTIVITY*/
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        initViewModel();
        initWidget();
        Home();
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
                sharedPreferences.getString(Config.USER_JENIS_KELAMIN,"")
        );

        mainActivityViewModel.setProfileData(profileData);
    }

    /*INIT WIDGET HALAMAN MAIN ACTIVITY (SEARCH VIEW DAN BOTNAV)*/
    private void initWidget(){

        /*SEARCH VIEW*/
        searchview = findViewById(R.id.svMainActivity);
        searchview.setQueryHint("Pencarian");
        searchview.setIconified(false);
        searchview.clearFocus();

        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("Pilih Speedboat");
                searchview.setIconified(false);
                searchview.clearFocus();
                return true;
            }
        });

        /*FRAGMENT FILLER KE FRAMELAYOUT*/
        fragmentFiller();


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
    }

    /*MEMASUKKAN SEMUA FRAGMENT KE DALAM FRAMELAYOUT*/
    private void fragmentFiller() {
        /*FRAGMENT NOTIFICATION*/
        notificationFragment = new NotificationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,notificationFragment,PEMESASNAN_FRAGMENT_TAG).commit();

        /*FRAGMENT PEMESANAN*/
        pemesananFragment = new PemesananFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,pemesananFragment,HOME_FRAGMENT_TAG).commit();

        /*FRAGMENT HOME*/
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,homeFragment,HOME_FRAGMENT_TAG).commit();

        /*FRAGMENT PROFILE*/
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,profileFragment,HOME_FRAGMENT_TAG).commit();

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
        getSupportFragmentManager().beginTransaction().hide(notificationFragment).commit();
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
            pemesananFragment = new PemesananFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,pemesananFragment,PEMESASNAN_FRAGMENT_TAG).commit();
            getSupportFragmentManager().beginTransaction().show(pemesananFragment).commit();
        }


        getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(notificationFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();
    }

    /*
    * METHOD MEMANGGIL FRAGMENT NOTIFICATION
    * */
    private void Notification(){
        if(notificationFragment != null){
            Log.d("TEST","TEST 5");
            getSupportFragmentManager().beginTransaction().show(notificationFragment).commit();
        }
        else if(!notificationFragment.isVisible()){
            Log.d("TEST","TEST 6");
            notificationFragment = new NotificationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.HomeFrameLayout,notificationFragment,NOTIFIKASI_FRAGMENT_TAG).commit();
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
        getSupportFragmentManager().beginTransaction().hide(notificationFragment).commit();
    }
}