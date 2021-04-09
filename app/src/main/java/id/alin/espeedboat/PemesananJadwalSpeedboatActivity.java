package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.function.Consumer;

import id.alin.espeedboat.MyAdapter.JadwalAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal.ServerResponseJadwalData;
import id.alin.espeedboat.MyRetrofit.Services.JadwalServices;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananJadwalSpeedboatActivity extends AppCompatActivity {
    /*BACK BUTTON*/
    private ImageButton imageButton;

    /*TEXT VIEW HEADER*/
    private TextView tvHeader;

    /*RECYCLER VIEW*/
    private RecyclerView recyclerView;

    /*RECYCLERVIEW ADAPTER*/
    private JadwalAdapter jadwalAdapter;

    /*LOADING LAYOUTE*/
    private LinearLayout loadingLayout;
    private LinearLayout nodatalayout;

    /*SQLITE*/
    private DatabaeESpeedboat database;

    /*FLOATING BUTTON*/
    private FloatingActionButton fabfilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_jadwal);
        initWidget();
        initDatabase();
        setLoadingState();
        getJadwalFromAPI();
    }

    private void initDatabase() {
        this.database = DatabaeESpeedboat.createDatabase(this);
    }

    private void initWidget() {
        /*BACK BUTTON*/
        if(this.imageButton == null){
            this.imageButton = findViewById(R.id.backButton);
        }

        /*TEXT HEADER*/
        if(this.tvHeader == null){
            this.tvHeader = findViewById(R.id.PemesananJadwalActivityHeader);
        }

        /*RECYCLER VIEW*/
        if(this.recyclerView == null){
            this.recyclerView = findViewById(R.id.rvPemesananJadwal);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            this.recyclerView.setLayoutManager(layoutManager);
        }

        /*LAYOUT BLOCKING SYSTEM*/
        if(this.loadingLayout == null){
            this.loadingLayout = findViewById(R.id.loadinglayout);
        }

        if(this.nodatalayout == null){
            this.nodatalayout = findViewById(R.id.nodatalayout);
        }

        /*FAB FILTER*/
        if(this.fabfilter == null){
            this.fabfilter = findViewById(R.id.fabFilter);
        }

        this.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setReadyState(){
        this.loadingLayout.setVisibility(View.INVISIBLE);
        this.fabfilter.setVisibility(View.VISIBLE);
        this.nodatalayout.setVisibility(View.INVISIBLE);
    }

    private void setLoadingState(){
        this.loadingLayout.setVisibility(View.VISIBLE);
        this.fabfilter.setVisibility(View.INVISIBLE);
        this.nodatalayout.setVisibility(View.INVISIBLE);
    }

    private void setNoDataState(){
        this.loadingLayout.setVisibility(View.INVISIBLE);
        this.fabfilter.setVisibility(View.INVISIBLE);
        this.nodatalayout.setVisibility(View.VISIBLE);
    }

    private void getJadwalFromAPI(){
        PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        JadwalServices jadwalServices = ApiClient.getRetrofit().create(JadwalServices.class);
        Call<ServerResponseJadwalData> call = jadwalServices.getJadwal(
                profileData.getToken(),
                pemesananSpeedboatData.getTanggal_variable(),
                String.valueOf(pemesananSpeedboatData.getId_asal()),
                String.valueOf(pemesananSpeedboatData.getId_tujuan()),
                JadwalServices.SPEEDBOAT
        );

        call.enqueue(new Callback<ServerResponseJadwalData>() {
            @Override
            public void onResponse(Call<ServerResponseJadwalData> call, Response<ServerResponseJadwalData> response) {
                Log.d("ALINDEBUG",String.valueOf(response.body().getJadwal().size()));
                if(response.body().getJadwal().size() == 0 || !response.body().getResponse_code().matches("200")){
                    setNoDataState();
                }
                else{

                    PemesananJadwalSpeedboatActivity.this.database.jadwalDAO().truncateJadwalEntity();

                    response.body().getJadwal().forEach(new Consumer<JadwalEntity>() {
                        @Override
                        public void accept(JadwalEntity jadwalEntity) {
                            PemesananJadwalSpeedboatActivity.this.database.jadwalDAO().insertJadwalEntity(jadwalEntity);
                        }
                    });

                    setReadyState();

                    String html = "Berhasil menemukan <b>"+response.body().getJadwal().size()+"</b> jadwal";

                    PemesananJadwalSpeedboatActivity.this.tvHeader.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

                    fillRecyclerView(response.body().getJadwal());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseJadwalData> call, Throwable t) {
                setNoDataState();
                Toast.makeText(PemesananJadwalSpeedboatActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillRecyclerView(List<JadwalEntity> jadwalEntities) {
        if(this.jadwalAdapter == null){
            this.jadwalAdapter = new JadwalAdapter(this,jadwalEntities);
        }

        this.recyclerView.setAdapter(this.jadwalAdapter);
    }
}