package id.alin.espeedboat.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import id.alin.espeedboat.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    CardView point, riwayat, active_trans, unpaid_trans, review, setting, logout;
    TextView viewUser;

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
    }

    private void eventListener(){
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Detail Profile", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Ini Pengaturan Akun User", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ini Tombol Logout", Toast.LENGTH_SHORT).show();
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
        refreshLayout = view.findViewById(R.id.srFragmentHome);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(view);
                eventListener();
                Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }
}