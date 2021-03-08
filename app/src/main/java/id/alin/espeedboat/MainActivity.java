package id.alin.espeedboat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.alin.espeedboat.Fragment.HomeFragment;
import id.alin.espeedboat.Fragment.NotificationFragment;
import id.alin.espeedboat.Fragment.PemesananFragment;
import id.alin.espeedboat.Fragment.ProfileFragment;

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

    private static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
    private static final String PEMESASNAN_FRAGMENT_TAG = "PEMESASNAN_FRAGMENT_TAG";
    private static final String NOTIFIKASI_FRAGMENT_TAG = "NOTIFIKASI_FRAGMENT_TAG";
    private static final String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        Home();
    }

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

        /*
        * FRAGMENT HOME MERUPAKAN FRAGMENT YANG PERTAMA KALI DIPANGGIL
        * */
        Home();

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