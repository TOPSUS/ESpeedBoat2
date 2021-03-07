package id.alin.espeedboat.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.alin.espeedboat.MyAdapter.DaftarPelabuhanAdapter;
import id.alin.espeedboat.R;


public class HomeFragment extends Fragment {
    /*VIEW*/
    private View view;

    /*GET SWIPE REFRESH*/
    private SwipeRefreshLayout swipeRefreshLayout;

    /*BAGIAN PROFILE*/
    private ShimmerFrameLayout shimmerprofile;
    private CircleImageView ciprofile;
    private TextView tvname,tvemail;
    private Button btnpointss, btnpayments;

    /*BAGIAN PELABUHAN*/
    private ShimmerFrameLayout shimmerberitapelabuhan;
    private TextView tvberitapelabuhantitla,tvberitapelabuhandetail;
    private RecyclerView rvberitapelabuhan;

    /*BGAIAN ESPEED NEWS*/
    private ShimmerFrameLayout shimmerespeednews;
    private TextView tvespeednewstitle, tvespeednewsdetail;
    private RecyclerView rvespeednews;

    /*WARNA BACKGROUND SHIMMER*/
    private static final String SHAMMER_BACKGROUND ="#BBE6FA";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;

        /*MENGHENTIKAN SEMUA SHIUMMER*/
        showShimmerProfile(false);
        showShimmerBeritaPelabuhan(false);
        showShimmerEspeedNews(false);

        swipeRefreshLayout = view.findViewById(R.id.srFragmentHomeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreashPage();
            }
        });

        /*INIT HALAMAN*/
        initViewProfile("I Putu Alin Winata Gotama","alingotama14@gmail.com");
        initBeritaPelabuhan();
        initEspeedNews();
    }

    private void refreashPage(){
        /*INIT PENGAMBILAN DATA*/
        getProfileFromApi();
        getBeritaPelabuhanFromApi();
        getEspeedNewsFromApi();
    }

    private void getProfileFromApi(){
        /*MENGUBAH DULU TAMPILAN DARI VIEW PROFILE*/
        this.tvname.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        this.tvemail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        this.tvname.setText("");
        this.tvemail.setText("");

        /*MENGAKTIFKAN SHIMMER*/
        showShimmerProfile(true);

        /*DISINI NANTI AKAN MEMANGGIL API DENGAN RETROFIT*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initViewProfile("Hacker Sejati ni bos maju sini","hacker@gmail.com");
            }
        },3000);
    }

    private void getBeritaPelabuhanFromApi(){
        /*MENGAKTIFKAN SHIMMER*/
        showShimmerBeritaPelabuhan(true);

        /*SET SEMUA VIEW INVISIBLE*/
        tvberitapelabuhantitla.setText("");
        tvberitapelabuhandetail.setText("");
        tvberitapelabuhantitla.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        tvberitapelabuhandetail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));

        /*DISINI NANTI AKAN MEMANGGIL API DENGAN RETROFIT*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initBeritaPelabuhan();
            }
        },7000);
    }

    private void getEspeedNewsFromApi(){
        /*AKTIFKAT SHIMMER*/
        showShimmerEspeedNews(true);

        /*SET SEMUA VIEW DI DALAMNYA JADI KOSONG*/
        tvespeednewstitle.setText("");
        tvespeednewsdetail.setText("");
        tvespeednewstitle.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        tvespeednewsdetail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));

        /*DISINI NANTI AKAN MEMANGGIL API DENGAN RETROFIT*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initEspeedNews();
            }
        },10000);
    }

    private void initViewProfile(String name, String email){
        /*INIT TV NAMA*/
        if(this.tvname == null){
            this.tvname = view.findViewById(R.id.tvFragmentHomeNama);
        }

        /*INIT TV EMAIL*/
        if(this.tvemail == null){
            this.tvemail = view.findViewById(R.id.tvFragmentHomeEmail);
        }

        /*MEMASUKKAN DATA BARU*/
        this.tvname.setText(name);
        this.tvemail.setText(email);
        this.tvname.setBackground(null);
        this.tvemail.setBackground(null);

        /*NONAKTIFKAN SHIMMER DAN REFRESH*/
        showShimmerProfile(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("SetTextI18n")
    private void initBeritaPelabuhan(){
        /*INIT RV BERITA PELABUHAN*/
        if(this.rvberitapelabuhan == null){
            this.rvberitapelabuhan = view.findViewById(R.id.rvHomeFragmentDaftarpelabuhan);
        }

        if(this.tvberitapelabuhantitla == null){
            this.tvberitapelabuhantitla = view.findViewById(R.id.tvFragmentHomeBeritaPelabuhanHeaderTitle);
        }

        if(this.tvberitapelabuhandetail == null){
            this.tvberitapelabuhandetail = view.findViewById(R.id.tvFragmentHomeBeritaPelabuhanHeaderDetail);
        }

        /*NONAKTIFKAN SHIMMER DAN REFRESH*/
        showShimmerBeritaPelabuhan(false);
        swipeRefreshLayout.setRefreshing(false);

        /*SET SEMUA VIEW INVISIBLE*/
        tvberitapelabuhantitla.setText("Berita Pelabuhan");
        tvberitapelabuhandetail.setText("Informasi akurat mengenai pelabuhan yang telah bekerja sama dengan ESpeedboat");
        tvberitapelabuhantitla.setBackground(null);
        tvberitapelabuhandetail.setBackground(null);

    }

    @SuppressLint("SetTextI18n")
    private void initEspeedNews(){
        /*INIT RV BERITA PELABUHAN*/
        if(this.rvespeednews == null){
            this.rvespeednews = view.findViewById(R.id.rvHomeFragmentEspeednews);
        }

        if(this.tvespeednewstitle == null){
            this.tvespeednewstitle = view.findViewById(R.id.tvFragmentHomeEspeedNewsTitle);
        }

        if(this.tvespeednewsdetail ==  null){
            this.tvespeednewsdetail = view.findViewById(R.id.tvFragmentHomeEspeedNewsDetail);
        }

        /*NONAKTIFKAN SHIMMER DAN REFRESH*/
        showShimmerEspeedNews(false);
        swipeRefreshLayout.setRefreshing(false);

        /*SET SEMUA VIEW INVISIBLE*/
        tvespeednewstitle.setText("ESpeed News");
        tvespeednewsdetail.setText("Berita aktual mengenai transportasi laut, ticket, dan berita terkini mengenai teknologi epseed");
        tvespeednewstitle.setBackground(null);
        tvespeednewsdetail.setBackground(null);
    }

    private void showShimmerProfile(boolean value){

        /*INIT WIDGET SHIMMER*/
        if(shimmerprofile == null){
            shimmerprofile = view.findViewById(R.id.shimmerFragmentHomeProfile);
        }

        if(value){
            shimmerprofile.showShimmer(true);
            shimmerprofile.startShimmer();
        }
        else{
            shimmerprofile.showShimmer(false);
            shimmerprofile.stopShimmer();
            shimmerprofile.hideShimmer();
        }

    }

    private void showShimmerBeritaPelabuhan(boolean value){

        /*INIT WIDGET SHIMMER*/
        if(shimmerberitapelabuhan == null){
            shimmerberitapelabuhan = view.findViewById(R.id.shimmerFragmentHomeBeritaPelabuhan);
        }

        if(value){
            shimmerberitapelabuhan.showShimmer(true);
            shimmerberitapelabuhan.startShimmer();
        }
        else{
            shimmerberitapelabuhan.showShimmer(false);
            shimmerberitapelabuhan.stopShimmer();
            shimmerberitapelabuhan.hideShimmer();
        }

    }

    private void showShimmerEspeedNews(boolean value){
        /*INIT WIDGET SHIMMER*/
        if(shimmerespeednews == null){
            shimmerespeednews = view.findViewById(R.id.shimmerFragmentHomeEspeednews);
        }

        if(value){
            shimmerespeednews.showShimmer(true);
            shimmerespeednews.startShimmer();
        }
        else{
            shimmerespeednews.showShimmer(false);
            shimmerespeednews.stopShimmer();
            shimmerespeednews.hideShimmer();
        }
    }
}