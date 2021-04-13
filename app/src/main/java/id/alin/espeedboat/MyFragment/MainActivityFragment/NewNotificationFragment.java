package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import id.alin.espeedboat.MyAdapter.NotificationAdapter;
import id.alin.espeedboat.R;

public class NewNotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    ArrayList<String> notifikasi;
    ArrayList<String> waktu;

    private ExpandableLayout expandableLayout;

    public NewNotificationFragment() {
        // Required empty public constructor
    }

    public static NewNotificationFragment newInstance(String param1, String param2) {
        NewNotificationFragment fragment = new NewNotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerViewNotification);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("DX",String.valueOf(dy));
                if(dy > 0){
                    NotificationFragment notificationFragment = (NotificationFragment) getParentFragment();
                    notificationFragment.expandableLayout.collapse();
                }else{
                    NotificationFragment notificationFragment = (NotificationFragment) getParentFragment();
                    notificationFragment.expandableLayout.expand();
                }
            }
        });
//        notifikasi = new ArrayList<>();
//        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
//        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
//
//        waktu = new ArrayList<>();
//        waktu.add("13 Januari 2021 13:00");
//        waktu.add("13 Januari 2021 13:00");
//
//        notificationAdapter = new NotificationAdapter(notifikasi, waktu, getContext());
//        recyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }




}