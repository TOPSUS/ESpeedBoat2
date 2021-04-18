package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyAdapter.GolonganAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Golongan.ServerResponseGolonganData;
import id.alin.espeedboat.MyRetrofit.Services.GolonganServices;
import id.alin.espeedboat.MyRoom.Entity.GolonganEntitiy;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CLASS FRAGMENT GOLOGAN DARI PELKABUHAN YANG DIPILIH
 */
public class FullscreenGolonganFragment extends DialogFragment {
    // ATRIBUTE
    private RecyclerView recyclerView;

    // LAYOUT
    private LinearLayout loading, nodata;
    private GolonganAdapter golonganAdapter;

    // BACK BUTTON
    private ImageButton backbutton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fullescreen_gologan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // INISIASI WIDGET DAN MEMBUAT STATE MENJADI LOADING
        initWidget();
        setStateLoading();

        // MEMANGGIL API DAN MENGAMBIL GOLONGAN BERDASARKAN ID PELABUHAN
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        getGolonganFromAPI(profileData.getToken(), String.valueOf(pemesananFeriData.getId_asal()));
    }

    // INISIASI WIDGET PADA HALAMAN
    private void initWidget() {
        // BUTTON
        this.backbutton = getView().findViewById(R.id.closebutton);
        this.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // LAYOUT
        this.loading = getView().findViewById(R.id.loadinglayout);
        this.nodata = getView().findViewById(R.id.nodatalayout);

        // RECYCLERVIEW
        this.recyclerView = getView().findViewById(R.id.rvgolongan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(layoutManager);
    }

    // API UNTUK MELAKUKAN MENDAPATKAN GOLONGAN YANG TERSEDIA PADA PELABUHAN
    private void getGolonganFromAPI(String token, String id_pelabuhan) {
        GolonganServices golonganServices = ApiClient.getRetrofit().create(GolonganServices.class);

        Call<ServerResponseGolonganData> call = golonganServices.getGolongan(
                token,
                id_pelabuhan
        );

        call.enqueue(new Callback<ServerResponseGolonganData>() {
            @Override
            public void onResponse(Call<ServerResponseGolonganData> call, Response<ServerResponseGolonganData> response) {
                if (isVisible()) {
                    if (response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")) {
                        if (response.body().getGolongans().size() > 0) {
                            fillRecyclerView(response.body().getGolongans());
                            setStateReady();
                        } else {
                            setStateNoData();
                        }
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseGolonganData> call, Throwable t) {
                if (isVisible()) {
                    Toast.makeText(getContext(), "TERJADI KESALAHAN" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // FILL RECYCLERVIEW DENGAN ADAPTER
    private void fillRecyclerView(List<GolonganEntitiy> golonganEntitiys) {
        if (this.golonganAdapter == null) {
            this.golonganAdapter = new GolonganAdapter(golonganEntitiys, getContext(), this);
            this.recyclerView.setAdapter(this.golonganAdapter);
        } else {
            this.golonganAdapter.golonganEntitiys = golonganEntitiys;
            this.golonganAdapter.notifyDataSetChanged();
        }
    }

    private void setStateLoading() {
        this.loading.setVisibility(View.VISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }

    private void setStateNoData() {
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.VISIBLE);
    }

    private void setStateReady() {
        this.loading.setVisibility(View.INVISIBLE);
        this.nodata.setVisibility(View.INVISIBLE);
    }
}
