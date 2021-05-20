package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import de.hdodenhof.circleimageview.CircleImageView;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyAdapter.BeritaEspeedAdapter;
import id.alin.espeedboat.MyAdapter.BeritaPelabuhanAdapter;
import id.alin.espeedboat.MyHelper.LogoutDataCleaner;
import id.alin.espeedboat.MyPointActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaEspeed.ServerResponseBeritaEspeed;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaPelabuhan.ServerResponseBeritaPelabuhan;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.BeritaPelabuhanServices;
import id.alin.espeedboat.MyRetrofit.Services.UserServices;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.BeritaEspeedEntity;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyUnpaidTransactionActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


public class HomeFragment extends Fragment implements LifecycleOwner {
    //*VIEW*/
    private View view;

    //*GET SWIPE REFRESH*/
    private SwipeRefreshLayout swipeRefreshLayout;

    //*BAGIAN PROFILE*/
    private ShimmerFrameLayout shimmerprofile;
    private CircleImageView ciprofile;
    private TextView tvname, tvemail;
    private Button btnpointss, btnpayments;

    //*BAGIAN PELABUHAN*/
    private ShimmerFrameLayout shimmerberitapelabuhan;
    private TextView tvberitapelabuhantitla, tvberitapelabuhandetail;
    private RecyclerView rvberitapelabuhan;
    private List<BeritaPelabuhanEntity> beritaPelabuhanEntities;
    private BeritaPelabuhanAdapter beritaPelabuhanAdapter;

    //*BGAIAN ESPEED NEWS*/
    private ShimmerFrameLayout shimmerespeednews;
    private TextView tvespeednewstitle, tvespeednewsdetail;
    private RecyclerView rvespeednews;
    private List<BeritaEspeedEntity> beritaEspeedEntities;
    private BeritaEspeedAdapter beritaEspeedAdapter;

    //*WARNA BACKGROUND SHIMMER*/
    private static final String SHAMMER_BACKGROUND = "#BBE6FA";

    //*ESPEEDBOAT DATABASE*/
    private DatabaeESpeedboat databaeESpeedboat;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public HomeFragment() {
        // FRAGMENT HARUS TETAP KOSONG CONTRUCTORNYA !!!!!!!!
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;

        /*INIT HALAMAN*/
        initEspeedDatabase();
        initViewProfile();
        initBeritaPelabuhan();
        initEspeedNews();
    }

    //*UNTUK REFRESH HALAMAN HOME FRAGMENT*/
    private void refreashPage() {
        /*INIT PENGAMBILAN DATA*/
        getProfileFromApi();
        getBeritaPelabuhanFromApi();
        getEspeedNewsFromApi();
    }

    // MENDAPATKAN DATA PROFILE DARI SERVER
    private void getProfileFromApi() {
        /*MENGUBAH DULU TAMPILAN DARI VIEW PROFILE*/
        this.tvname.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        this.tvemail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        this.tvname.setText("");
        this.tvemail.setText("");

        /*MENGAKTIFKAN SHIMMER*/
        showShimmerProfile(true);

        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        if (data != null) {
            UserServices services = ApiClient.getRetrofit().create(UserServices.class);


            Call<ServerResponseProfileData> call = services.userprofile(
                    data.getToken()
            );

            call.enqueue(new Callback<ServerResponseProfileData>() {
                @Override
                public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                    ServerResponseProfileData newData = response.body();

                    if (response.body().getResponse_code().equals("200") && response.body().getStatus().equals("success")) {
                        data.setUser_id(newData.getUser_id());
                        data.setName(newData.getName());
                        data.setAlamat(newData.getAlamat());
                        data.setChat_id(newData.getChat_id());
                        data.setPin(newData.getPin());
                        data.setEmail(newData.getEmail());
                        data.setNohp(newData.getNohp());
                        data.setJeniskelamin(newData.getJeniskelamin());

                        MainActivity.mainActivityViewModel.setProfileData(data);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                sharedPreferences = getContext().getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();

                                editor.putString(Config.USER_NAMA, data.getName());
                                editor.putString(Config.USER_ALAMAT, data.getAlamat());
                                editor.putString(Config.USER_CHAT_ID, data.getChat_id());
                                editor.putString(Config.USER_PIN, data.getPin());
                                editor.putString(Config.USER_EMAIL, data.getEmail());
                                editor.putString(Config.USER_FOTO, data.getFoto());
                                editor.putString(Config.USER_NOHP, data.getNohp());
                                editor.putString(Config.USER_JENIS_KELAMIN, data.getJeniskelamin());
                                editor.apply();

                            }
                        });

                        Log.d("RETROFIT", "berhasil");
                    } else if (response.body().getResponse_code().equals("401") && response.body().getStatus().equals("failure")) {

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        LogoutDataCleaner logoutDataCleaner = new LogoutDataCleaner(getContext());
                        logoutDataCleaner.clearSharedPreferences();
                        startActivity(intent);
                        getActivity().finish();

                    } else {
                        MainActivity.mainActivityViewModel.setProfileData(data);
                        StringBuilder responseServer = new StringBuilder();

                        responseServer.append("SERVER : ");
                        responseServer.append(response.body().getMessage());

                        Snackbar.make(getActivity().findViewById(R.id.parentlayoutMainActivity), "TERJADI KESALAHAN", 3000)
                                .setAnchorView(R.id.botnavbarMainActivity)
                                .setBackgroundTint(ContextCompat.getColor(getContext(), R.color.dangerColor))
                                .show();

                        MainActivity.mainActivityViewModel.setProfileData(data);
                    }

                }

                @Override
                public void onFailure(Call<ServerResponseProfileData> call, Throwable t) {
                    Snackbar.make(getActivity().findViewById(R.id.parentlayoutMainActivity), "GAGAL MELAKUKAN REFRESH", 3000)
                            .setAnchorView(R.id.botnavbarMainActivity)
                            .setBackgroundTint(ContextCompat.getColor(getContext(), R.color.dangerColor))
                            .show();

                    MainActivity.mainActivityViewModel.setProfileData(data);
                }
            });

        }

    }

    // MENDAPATKAN BERITA PELABUHAN DARI SERVER
    private void getBeritaPelabuhanFromApi() {
        /*MENGAKTIFKAN SHIMMER ATAU LOADING PAGE*/
        showShimmerBeritaPelabuhan(true);

        /*NAH AMBIL LIVE DATA BUAT GET TOKEN USER SAAT INI*/
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        /*MEMANGGIL SERVICE BERITA PELABUHAN RETROFIT*/
        BeritaPelabuhanServices services = ApiClient.getRetrofit().create(BeritaPelabuhanServices.class);
        Call<ServerResponseBeritaPelabuhan> call = services.readAllBeritaPelabuhan(
                data.getToken()
        );

        /*
         *
         * MENJALANKAN BEBERAPA
         *
         * */
        call.enqueue(new Callback<ServerResponseBeritaPelabuhan>() {

            /*
             *
             * SAAT RESPONSENYA BERHASIL MAKA AKAN MEMPERBARUI RECYLERVIEW
             * DAN DISIIMPAN KE DALAM SQLITE MENGGUNAKAN THREAD TERPISAH
             *
             * */
            @Override
            public void onResponse(Call<ServerResponseBeritaPelabuhan> call, Response<ServerResponseBeritaPelabuhan> response) {
                if (response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")) {
                    HomeFragment.this.beritaPelabuhanEntities.clear();
                    HomeFragment.this.beritaPelabuhanEntities = response.body().getBerita_pelabuhan();
                    HomeFragment.this.beritaPelabuhanAdapter.beritaPelabuhanEntities = response.body().getBerita_pelabuhan();
                    HomeFragment.this.beritaPelabuhanAdapter.notifyDataSetChanged();

                    /*NONAKTIFKAN SHIMMER DAN REFRESH*/
                    showShimmerBeritaPelabuhan(false);

                    /*MENYIMPAN DATA DARI SERVER BERITA PELABUHAN SECARA ASYC*/
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            /*HAPUS SEMUA DATA YANG ADA DI DALAM TABLE*/
                            HomeFragment.this.databaeESpeedboat.beritaPelabuhanDAO().truncateBeritaPelabuhanEntity();

                            /*MASUKKAN DATA SATU PERSATU KE DALAM SQLITE*/
                            response.body().getBerita_pelabuhan().forEach(new Consumer<BeritaPelabuhanEntity>() {
                                @Override
                                public void accept(BeritaPelabuhanEntity entity) {
                                    HomeFragment.this.databaeESpeedboat.beritaPelabuhanDAO().insertBeritaPelabuhan(entity);
                                }
                            });
                        }
                    });
                } else if (response.body().getStatus().matches("failure") && response.body().getResponse_code().matches("401")) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    LogoutDataCleaner logoutDataCleaner = new LogoutDataCleaner(getContext());
                    logoutDataCleaner.clearSharedPreferences();
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseBeritaPelabuhan> call, Throwable t) {
                Log.d("BERITA", t.getMessage());

                /*NONAKTIFKAN SHIMMER DAN REFRESH*/
                showShimmerBeritaPelabuhan(false);
            }
        });
    }

    // MENDAPATKAN BERITA ESPEED NEWS DARI SERVER
    private void getEspeedNewsFromApi() {
        /*AKTIFKAT SHIMMER*/
        showShimmerEspeedNews(true);

        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        BeritaPelabuhanServices beritaPelabuhanServices = ApiClient.getRetrofit().create(BeritaPelabuhanServices.class);

        Call<ServerResponseBeritaEspeed> call = beritaPelabuhanServices.readAllBeritaKapal(
                profileData.getToken()
        );

        call.enqueue(new Callback<ServerResponseBeritaEspeed>() {
            @Override
            public void onResponse(Call<ServerResponseBeritaEspeed> call, Response<ServerResponseBeritaEspeed> response) {
                if (response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")) {
                    HomeFragment.this.beritaEspeedEntities.clear();
                    HomeFragment.this.beritaEspeedEntities = response.body().getBerita_espeed();
                    HomeFragment.this.beritaEspeedAdapter.beritaEspeedEntities = response.body().getBerita_espeed();
                    HomeFragment.this.beritaEspeedAdapter.notifyDataSetChanged();

                    /*NONAKTIFKAN SHIMMER DAN REFRESH*/
                    showShimmerEspeedNews(false);

                    HomeFragment.this.databaeESpeedboat.beritaEspeedDAO().truncateBeritaEspeed();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            HomeFragment.this.beritaEspeedEntities.forEach(new Consumer<BeritaEspeedEntity>() {
                                @Override
                                public void accept(BeritaEspeedEntity beritaEspeedEntity) {
                                    HomeFragment.this.databaeESpeedboat.beritaEspeedDAO().insertBeritaEspeed(beritaEspeedEntity);
                                }
                            });
                        }
                    });
                }else if (response.body().getResponse_code().matches("401") && response.body().getStatus().matches("failure")){
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    LogoutDataCleaner logoutDataCleaner = new LogoutDataCleaner(getContext());
                    logoutDataCleaner.clearSharedPreferences();
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseBeritaEspeed> call, Throwable t) {
                /*NONAKTIFKAN SHIMMER DAN REFRESH*/
                showShimmerEspeedNews(false);
            }
        });
    }

    // INISIASI DATABASE SQLITE
    private void initEspeedDatabase() {
        if (databaeESpeedboat == null) {
            this.databaeESpeedboat = DatabaeESpeedboat.createDatabase(getContext());
        }
    }

    // INISIASI VIEW PROFILE
    private void initViewProfile() {
        /*INIT REFRESH*/
        swipeRefreshLayout = view.findViewById(R.id.srFragmentHomeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreashPage();
            }
        });

        this.btnpayments = getView().findViewById(R.id.btnFragmentHome);
        this.btnpayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyUnpaidTransactionActivity.class);
                intent.putExtra("status", "menunggu pembayaran");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        this.btnpointss = getView().findViewById(R.id.appCompatButton);
        this.btnpointss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPointActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        /*INIT TV NAMA*/
        if (this.tvname == null) {
            this.tvname = view.findViewById(R.id.tvFragmentHomeNama);
        }

        /*INIT TV EMAIL*/
        if (this.tvemail == null) {
            this.tvemail = view.findViewById(R.id.tvFragmentHomeEmail);
        }

        /*INIT GAMBAR*/
        if (this.ciprofile == null) {
            this.ciprofile = view.findViewById(R.id.circleImageView);
        }

        /*MEMASUKKAN DATA BARU*/
        MainActivity.mainActivityViewModel.getProfileLiveData().observe(this, new Observer<ProfileData>() {


            @Override
            public void onChanged(ProfileData data) {
                HomeFragment.this.tvname.setText(data.getName());
                HomeFragment.this.tvemail.setText(data.getEmail());

                StringBuilder url = new StringBuilder(ApiClient.BASE_IMAGE_USER);
                url.append(data.getFoto());

                Glide.with(getContext()).load(url.toString())
                        .placeholder(R.drawable.user_placeholder)
                        .into(HomeFragment.this.ciprofile);

                HomeFragment.this.tvname.setBackground(null);
                HomeFragment.this.tvemail.setBackground(null);

                /*NONAKTIFKAN SHIMMER DAN REFRESH*/
                showShimmerProfile(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        HomeFragment.this.tvname.setBackground(null);
        HomeFragment.this.tvemail.setBackground(null);

        /*NONAKTIFKAN SHIMMER DAN REFRESH*/
        showShimmerProfile(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    // INISIASI INFO BERITA PELBUHAN
    private void initBeritaPelabuhan() {
        /*INIT RV BERITA PELABUHAN*/
        if (this.rvberitapelabuhan == null) {
            this.rvberitapelabuhan = view.findViewById(R.id.rvHomeFragmentDaftarpelabuhan);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            ;
            this.rvberitapelabuhan.setLayoutManager(layoutManager);
        }

        /*INIT TV TITLE BERITA PELABUHAN*/
        if (this.tvberitapelabuhantitla == null) {
            this.tvberitapelabuhantitla = view.findViewById(R.id.tvFragmentHomeBeritaPelabuhanHeaderTitle);
        }

        /*INIT DETAIL TV BERITA PELABUHAN*/
        if (this.tvberitapelabuhandetail == null) {
            this.tvberitapelabuhandetail = view.findViewById(R.id.tvFragmentHomeBeritaPelabuhanHeaderDetail);
        }

        /*CEK DATABASE SQLITE MELIHAT DATA BERITA*/
        this.beritaPelabuhanEntities = this.databaeESpeedboat.beritaPelabuhanDAO().getAllBeritaPelabuhanEntity();

        /*APABILA KOSONG MAKA RECYCLERVIEW DUMMY AKAN DIBUAT KEMUDIAN MEMANGGIL API BERITA BOAT*/
        if (this.beritaPelabuhanEntities.isEmpty()) {
            BeritaPelabuhanEntity dummyBeritaPelabuhan = new BeritaPelabuhanEntity();
            dummyBeritaPelabuhan.setBerita("");
            dummyBeritaPelabuhan.setJudul("");
            dummyBeritaPelabuhan.setId(-1);
            dummyBeritaPelabuhan.setFoto("");
            dummyBeritaPelabuhan.setTanggal("");

            List<BeritaPelabuhanEntity> listBeritaDummy = new LinkedList<>();
            listBeritaDummy.add(dummyBeritaPelabuhan);

            this.beritaPelabuhanAdapter = new BeritaPelabuhanAdapter(listBeritaDummy, getContext());
        } else {
            this.beritaPelabuhanAdapter = new BeritaPelabuhanAdapter(this.beritaPelabuhanEntities, getContext());
        }

        /*PASANG ADAPTER KE RECYCLERVIEW*/
        this.rvberitapelabuhan.setAdapter(this.beritaPelabuhanAdapter);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(this.rvberitapelabuhan);
        ScrollingPagerIndicator indicator = view.findViewById(R.id.irvBeritaPelabuhan);
        indicator.setSelectedDotColor(Color.BLUE);
        indicator.attachToRecyclerView(this.rvberitapelabuhan);

        /*MENJALANKAN GET BERITA PELABUHAN KE API*/
        getBeritaPelabuhanFromApi();
    }

    @SuppressLint("SetTextI18n")
    private void initEspeedNews() {
        /*INIT RV BERITA PELABUHAN*/
        if (this.rvespeednews == null) {
            this.rvespeednews = view.findViewById(R.id.rvHomeFragmentEspeednews);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            ;
            this.rvespeednews.setLayoutManager(layoutManager);
        }

        if (this.tvespeednewstitle == null) {
            this.tvespeednewstitle = view.findViewById(R.id.tvFragmentHomeEspeedNewsTitle);
        }

        if (this.tvespeednewsdetail == null) {
            this.tvespeednewsdetail = view.findViewById(R.id.tvFragmentHomeEspeedNewsDetail);
        }

        this.beritaEspeedEntities = this.databaeESpeedboat.beritaEspeedDAO().getAllBeritaEspeed();

        if (this.beritaEspeedEntities.isEmpty()) {
            BeritaEspeedEntity beritaEspeedEntity = new BeritaEspeedEntity();
            beritaEspeedEntity.setId(-1);
            beritaEspeedEntity.setId_speedboat(-1);
            beritaEspeedEntity.setId_user(-1);
            beritaEspeedEntity.setFoto("default.jpg");
            beritaEspeedEntity.setJudul("BERITA ESPEEDBOAT");
            beritaEspeedEntity.setBerita("BERITA ESPEEDBOAT");
            beritaEspeedEntity.setTanggal("2020-01-01");

            List<BeritaEspeedEntity> dummyberitaEspeed = new LinkedList<>();
            dummyberitaEspeed.add(beritaEspeedEntity);

            this.beritaEspeedAdapter = new BeritaEspeedAdapter(dummyberitaEspeed, getContext());
        } else {
            this.beritaEspeedAdapter = new BeritaEspeedAdapter(this.beritaEspeedEntities, getContext());
        }

        this.rvespeednews.setAdapter(this.beritaEspeedAdapter);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(this.rvespeednews);
        ScrollingPagerIndicator indicator = view.findViewById(R.id.irvBeritaSpeedBoat);
        indicator.setSelectedDotColor(Color.BLUE);
        indicator.attachToRecyclerView(this.rvespeednews);

        getEspeedNewsFromApi();
    }

    // SHOW SHIMMER PROFILE "EFFECT LOADING"
    private void showShimmerProfile(boolean value) {

        /*INIT WIDGET SHIMMER*/
        if (shimmerprofile == null) {
            shimmerprofile = view.findViewById(R.id.shimmerFragmentHomeProfile);
        }

        if (value) {
            shimmerprofile.showShimmer(true);
            shimmerprofile.startShimmer();
        } else {
            shimmerprofile.showShimmer(false);
            shimmerprofile.stopShimmer();
            shimmerprofile.hideShimmer();
        }

    }

    // SHOW SHIMMER BERITA PELBUHAN "EFFECT LOADING"
    private void showShimmerBeritaPelabuhan(boolean value) {

        /*INIT WIDGET SHIMMER*/
        if (shimmerberitapelabuhan == null) {
            shimmerberitapelabuhan = view.findViewById(R.id.shimmerFragmentHomeBeritaPelabuhan);
        }

        if (value) {
            shimmerberitapelabuhan.showShimmer(true);
            shimmerberitapelabuhan.startShimmer();

            /*SET SEMUA VIEW INVISIBLE*/
            tvberitapelabuhantitla.setText("");
            tvberitapelabuhandetail.setText("");
            tvberitapelabuhantitla.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
            tvberitapelabuhandetail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        } else {
            shimmerberitapelabuhan.showShimmer(false);
            shimmerberitapelabuhan.stopShimmer();
            shimmerberitapelabuhan.hideShimmer();

            swipeRefreshLayout.setRefreshing(false);

            /*SET SEMUA VIEW INVISIBLE*/
            tvberitapelabuhantitla.setText("Berita Pelabuhan");
            tvberitapelabuhandetail.setText("Informasi akurat mengenai pelabuhan yang telah bekerja sama dengan ESpeedboat");
            tvberitapelabuhantitla.setBackground(null);
            tvberitapelabuhandetail.setBackground(null);
        }

    }

    // SHOW SHIMMER ESPEED NEWS "EFFECT LOADING"
    @SuppressLint("SetTextI18n")
    private void showShimmerEspeedNews(boolean value) {
        /*INIT WIDGET SHIMMER*/
        if (shimmerespeednews == null) {
            shimmerespeednews = view.findViewById(R.id.shimmerFragmentHomeEspeednews);
        }

        if (value) {
            shimmerespeednews.showShimmer(true);
            shimmerespeednews.startShimmer();

            tvespeednewstitle.setText("");
            tvespeednewsdetail.setText("");
            tvespeednewstitle.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
            tvespeednewsdetail.setBackgroundColor(Color.parseColor(HomeFragment.SHAMMER_BACKGROUND));
        } else {
            shimmerespeednews.showShimmer(false);
            shimmerespeednews.stopShimmer();
            shimmerespeednews.hideShimmer();
            tvespeednewstitle.setText("ESpeed News");
            tvespeednewsdetail.setText("Berita aktual mengenai transportasi laut, ticket, dan berita terkini mengenai teknologi epseed");
            tvespeednewstitle.setBackground(null);
            tvespeednewsdetail.setBackground(null);
        }
    }
}