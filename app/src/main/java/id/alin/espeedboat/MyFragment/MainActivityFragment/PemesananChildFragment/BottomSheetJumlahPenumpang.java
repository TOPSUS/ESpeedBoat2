package id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyHelper.LogoutDataCleaner;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MaxJumlahPenumpang.ServerResponseMaxJumlahPenumpang;
import id.alin.espeedboat.MyRetrofit.Services.MaxJumlahPenumpangServices;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetJumlahPenumpang extends BottomSheetDialogFragment {
    private MaterialNumberPicker materialNumberPicker;
    private Button btnPilih;
    private LinearLayout loadinglayout;

    // ATRIBUTE FORM
    private String form;

    // PRIVATE MAX VALUE;
    private static final int DEFAULTMAXVALUE = 10;

    // PRIVATE STATIC FORM YANG AKAN DIISI
    public static final String FORM = "FORM";
    public static final String SPEEDBOAT = "SPEEDBOAT";
    public static final String FERI = "FERI";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheetjumlahpenumpangpemesananfragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        form = getArguments().getString(FORM,"");

        initWidget();

        if(form.matches(FERI)){
            Toast.makeText(getContext(), "MASUK", Toast.LENGTH_SHORT).show();
            initEventFeri();
        }else if(form.matches(SPEEDBOAT)){
            initSpeedBoat();
        }
    }

    private void initWidget(){
        this.materialNumberPicker = getView().findViewById(R.id.bottomsheetJumlahPenumpang);
        this.loadinglayout = getView().findViewById(R.id.loadinglayout);
        this.btnPilih = getView().findViewById(R.id.btnPilihJumlahPenumpang);

        this.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form.matches(SPEEDBOAT)){
                    PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
                    pemesananSpeedboatData.setJumlah_penumpang(String.valueOf(materialNumberPicker.getValue()));
                    MainActivity.mainActivityViewModel.setPemesananSpeedboatData(pemesananSpeedboatData);
                }else if(form.matches(FERI)){
                    Toast.makeText(getContext(), "MASUK 2", Toast.LENGTH_SHORT).show();
                    PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                    pemesananFeriData.setJumlah_penumpang(materialNumberPicker.getValue());
                    MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                }
                dismiss();
            }
        });
    }

    private void initEventFeri(){
        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
        ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();

        if(pemesananFeriData.getTipe_jasa().matches(FeriFragment.KENDARAAN)){
            getMaxPenumpangFromAPI(pemesananFeriData.getId_golongan_kendaraan(),profileData.getToken());
        }else{
            this.materialNumberPicker.setMaxValue(DEFAULTMAXVALUE);
        }


    }

    private void initSpeedBoat() {
    }

    private void setStateLoading(boolean status){
        if(status){
            this.loadinglayout.setVisibility(View.VISIBLE);
        }else{
            this.loadinglayout.setVisibility(View.GONE);
        }
    }

    private void getMaxPenumpangFromAPI(long id_golongan, String token){

        setStateLoading(true);

        MaxJumlahPenumpangServices services = ApiClient.getRetrofit().create(MaxJumlahPenumpangServices.class);
        Call<ServerResponseMaxJumlahPenumpang> call = services.getMaxPenumpang(
            token,
            String.valueOf(id_golongan)
        );

        call.enqueue(new Callback<ServerResponseMaxJumlahPenumpang>() {
            @Override
            public void onResponse(Call<ServerResponseMaxJumlahPenumpang> call, Response<ServerResponseMaxJumlahPenumpang> response) {
                if(response.body().getResponse_code().matches("200")){
                    BottomSheetJumlahPenumpang.this.materialNumberPicker.setMaxValue(response.body().getMaximal_penumpang());
                }else if(response.body().getResponse_code().matches("401")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    LogoutDataCleaner logoutDataCleaner = new LogoutDataCleaner(getContext());
                    logoutDataCleaner.clearSharedPreferences();
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
                }

                setStateLoading(false);
            }

            @Override
            public void onFailure(Call<ServerResponseMaxJumlahPenumpang> call, Throwable t) {
                setStateLoading(false);
                dismiss();
                Toast.makeText(getContext(), "GAGAL, PERIKSA KONEKSI ANDA", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
