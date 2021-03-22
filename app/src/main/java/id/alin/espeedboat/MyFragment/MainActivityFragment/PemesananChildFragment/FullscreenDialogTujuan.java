package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyAdapter.PelabuhanExpandedAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan.ServerResponsePelabuhanData;
import id.alin.espeedboat.MyRetrofit.Services.PelabuhanServices;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenDialogTujuan extends DialogFragment implements LifecycleOwner {
    private LinearLayout loading, nodata;
    private RecyclerView rvfullscreenpelabuhan;
    private PelabuhanExpandedAdapter pelabuhanExpandedAdapter;
    private ImageButton closebutton;

    /*METHO STATIC UNTUK MEMBUAT INSTANCE BARU*/
    public static FullscreenDialogTujuan createNewInstance(){
        return new FullscreenDialogTujuan();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fullscreenpemesananjadwalchildfragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidget();
        setStateLoading();
        getDataPelabuhanFromAPI();
    }

    /*INIT WIDGET PERTAMA KALI*/
    private void initWidget(){
        this.loading = getView().findViewById(R.id.loaddinglayoutFullScreeenPemesananJadwalChildFragment);
        this.nodata = getView().findViewById(R.id.nodatalayoutFullScreeenPemesananJadwalChildFragment);
        this.rvfullscreenpelabuhan = getView().findViewById(R.id.rvFullScreenPemesananChildFragment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.rvfullscreenpelabuhan.setLayoutManager(layoutManager);
        this.closebutton = getView().findViewById(R.id.closebutton);
        this.closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /*MENGAMBIL DATA PELABUHAN DARI API PELABUHAN*/
    private void getDataPelabuhanFromAPI(){

        String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();

        PelabuhanServices pelabuhanServices = ApiClient.getRetrofit().create(PelabuhanServices.class);
        Call<ServerResponsePelabuhanData> call = pelabuhanServices.readPelabuhan(authorization);

        call.enqueue(new Callback<ServerResponsePelabuhanData>() {
            @Override
            public void onResponse(Call<ServerResponsePelabuhanData> call, Response<ServerResponsePelabuhanData> response) {
                if(isVisible()){
                    if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                        if(response.body().getPelabuhan().size() > 0){
                            setStateReady();
                            Log.d("STATE",String.valueOf(response.body().getPelabuhan().size()));
                            setRecyclerView(response.body().getPelabuhan());
                        }else{
                            setStateNodata();
                        }
                    }else{
                        setStateNodata();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponsePelabuhanData> call, Throwable t) {
                setStateNodata();
            }
        });
    }

    /*MENYIAPKAN RECYLERVIEW*/
    private void setRecyclerView(List<PelabuhanEntity> pelabuhanEntities) {
        this.pelabuhanExpandedAdapter = new PelabuhanExpandedAdapter(pelabuhanEntities, getContext(), this.rvfullscreenpelabuhan, this) {
            @Override
            public void onSelectedItemPelabuhan(PelabuhanEntity pelabuhanEntity) {
                PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                pemesananData.setTujuan(pelabuhanEntity.getNama_pelabuhan());
                pemesananData.setId_tujuan(pelabuhanEntity.getId());
                MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
            }
        };
        this.rvfullscreenpelabuhan.setAdapter(this.pelabuhanExpandedAdapter);
    }

    /*DIJALANKAN SAAT API BERHASIL MENGAMBIL DATA DARI SERVER DAN JUMLAHNYA LEBIH DARI 0*/
    private void setStateReady(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    /*STATE LOADING SAAT APLIKASI SEDANG MEMINTA DATA PELABUHAN KE SERVER*/
    private void setStateLoading(){
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    /*STATE NO DATA SAAT DATA YANG DIBERIKAN OLEH SERVER KOSONG*/
    private void setStateNodata(){
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }
}
