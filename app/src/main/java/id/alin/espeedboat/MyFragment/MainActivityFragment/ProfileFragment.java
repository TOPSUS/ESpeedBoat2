package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import id.alin.espeedboat.LoginActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyProfileActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ProfileData;
import id.alin.espeedboat.R;

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
    CardView point, riwayat, active_trans, unpaid_trans, review, setting, logout;
    TextView viewUser;

    TextView tvnama, tvemail;
    CircleImageView civfotoProfil;

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
        viewUser = view.findViewById(R.id.txtViewAccount);

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
                Toast.makeText(getContext(), "Ini Point User", Toast.LENGTH_SHORT).show();
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Riwayat Transaksi User", Toast.LENGTH_SHORT).show();
            }
        });

        active_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Transaksi Aktif User", Toast.LENGTH_SHORT).show();
            }
        });

        unpaid_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Transaksi Belum Dibayar User", Toast.LENGTH_SHORT).show();
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
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
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