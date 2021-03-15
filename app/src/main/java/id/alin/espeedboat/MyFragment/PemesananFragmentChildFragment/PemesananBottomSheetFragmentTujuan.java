package id.alin.espeedboat.MyFragment.PemesananFragmentChildFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyAdapter.PelabuhanAdapter;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan.ServerResponsePelabuhanData;
import id.alin.espeedboat.MyRetrofit.Services.PelabuhanServices;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananBottomSheetFragmentTujuan extends BottomSheetDialogFragment implements LifecycleOwner {
    private RecyclerView rvbottomsheetpemesanan;
    private RecyclerView.LayoutManager layoutManager;
    private PelabuhanAdapter pelabuhanAdapter;
    private DatabaeESpeedboat databaeESpeedboat;
    private List<PelabuhanEntity> pelabuhanEntities;
    private LinearLayout block;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheetpemesananfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initWidget();

        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        PelabuhanServices services = ApiClient.getRetrofit().create(PelabuhanServices.class);
        Call<ServerResponsePelabuhanData> call = services.readPelabuhan(
                data.getToken()
        );

        call.enqueue(new Callback<ServerResponsePelabuhanData>() {
            @Override
            public void onResponse(Call<ServerResponsePelabuhanData> call, Response<ServerResponsePelabuhanData> response) {
                if (response.body().getResponse_code().matches("200") && response.body().getPelabuhan().size() != 0) {
                    PemesananBottomSheetFragmentTujuan.this.pelabuhanEntities = response.body().getPelabuhan();
                    if (isVisible()) {
                        PemesananBottomSheetFragmentTujuan.this.block.setVisibility(View.GONE);
                        initRecyclerView();
                    }

                } else {
                    Log.d("MAKANEDENGIDE", "FAIL");
                }

            }

            @Override
            public void onFailure(Call<ServerResponsePelabuhanData> call, Throwable t) {

            }
        });


    }

    private void initWidget() {
        if (this.block == null) {
            this.block = getView().findViewById(R.id.bottomsheetBlockSystem);
        }
    }

    private void initRecyclerView() {
        if (this.pelabuhanAdapter == null) {
            this.pelabuhanAdapter = new PelabuhanAdapter(getContext(), this.pelabuhanEntities) {
                @Override
                public void getDataFomAdapter(PelabuhanEntity pelabuhanEntity) {
                    PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                    pemesananData.setTujuan(pelabuhanEntity.getNama_pelabuhan());
                    MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
                    PemesananBottomSheetFragmentTujuan.this.dismiss();
                }
            };
        }

        if (this.rvbottomsheetpemesanan == null) {
            this.rvbottomsheetpemesanan = getView().findViewById(R.id.rvBottomSheetFragmentPemesanan);
            this.layoutManager = new LinearLayoutManager(getContext());
            this.rvbottomsheetpemesanan.setLayoutManager(this.layoutManager);
            this.rvbottomsheetpemesanan.setAdapter(this.pelabuhanAdapter);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog dialogc = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = dialogc.findViewById(R.id.bottomsheetrootlayout);
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();


                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setDraggable(false);
                bottomSheetBehavior.setPeekHeight(300,false);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return bottomSheetDialog;
    }


}
