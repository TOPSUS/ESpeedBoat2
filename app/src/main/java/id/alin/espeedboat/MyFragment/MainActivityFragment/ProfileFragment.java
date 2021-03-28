package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyPointActivity;
import id.alin.espeedboat.MyPointHistoryActivity;
import id.alin.espeedboat.MyProfile.MyProfileActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRetrofit.Services.UserServices;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyUnpaidTransactionActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    CardView point, pointHistory, riwayat, active_trans, unpaid_trans, review, setting, logout;
    TextView viewUser;

    TextView tvnama, tvemail;
    CircleImageView civfotoProfil;

    /*SHARED PREFERENCE APLIKASI*/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ProgressDialog dialog;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view){
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);

        //PROGRES BAR INIT
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);

        viewUser = view.findViewById(R.id.txtViewAccount);

        pointHistory = view.findViewById(R.id.cardViewPointHistory);
        point = view.findViewById(R.id.cardViewPoint);
        riwayat = view.findViewById(R.id.cardHistory);
        active_trans = view.findViewById(R.id.cardViewActiveTrans);
        unpaid_trans = view.findViewById(R.id.cardViewUnpaidTrans);
        review = view.findViewById(R.id.cardViewReview);
        setting = view.findViewById(R.id.cardViewSetting);
        logout = view.findViewById(R.id.cardViewLogout);

        tvnama = view.findViewById(R.id.namaAccount);
        tvemail = view.findViewById(R.id.emailAccount);
        civfotoProfil = view.findViewById(R.id.fotoAccount);

        MainActivity.mainActivityViewModel.getProfileLiveData().observe(this, new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData data) {
                ProfileFragment.this.tvnama.setText(data.getName());
                ProfileFragment.this.tvemail.setText(data.getEmail());

                StringBuilder url = new StringBuilder(ApiClient.BASE_IMAGE_USER);
                url.append(data.getFoto());

                Glide.with(getContext()).load(url.toString())
                        .placeholder(R.drawable.user_placeholder)
                        .into(ProfileFragment.this.civfotoProfil);

            }
        });
    }

    private void eventListener(){
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPointActivity.class);
                startActivity(intent);
            }
        });

        pointHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPointHistoryActivity.class);
                startActivity(intent);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyUnpaidTransactionActivity.class);
                intent.putExtra("status", "done");
                startActivity(intent);
            }
        });

        active_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyUnpaidTransactionActivity.class);
                intent.putExtra("status", "terkonfirmasi");
                startActivity(intent);
            }
        });

        unpaid_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyUnpaidTransactionActivity.class);
                intent.putExtra("status", "menunggu pembayaran");
                startActivity(intent);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Ulasan Transaksi User", Toast.LENGTH_SHORT).show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                        .setTitle("Konfirmasi Logout")
                        .setMessage("Apakah anda yakin untuk logout?")
                        .setCancelable(false)
                        .setAnimation(R.raw.animation_boat_2)
                        .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                logoutFromApi();
                            }
                        })
                        .setNegativeButton("Batal", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }

                        })
                        .build();

                // Show Dialog
                mDialog.show();

            }
        });
    }

    private void logoutFromApi(){
        ProfileData data = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
        dialog.setMessage("Processing");
        dialog.show();
        UserServices services = ApiClient.getRetrofit().create(UserServices.class);
        Call<ServerResponseProfileData> call = services.logout(
                data.getToken(),
                data.getUser_id()

        );

        call.enqueue(new Callback<ServerResponseProfileData>() {
            @Override
            public void onResponse(Call<ServerResponseProfileData> call, Response<ServerResponseProfileData> response) {
                ServerResponseProfileData newData = response.body();
                if(response.body().getStatus().matches("success")){
                    clearSharedPreference();
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseProfileData> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void clearSharedPreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        eventListener();
    }
}