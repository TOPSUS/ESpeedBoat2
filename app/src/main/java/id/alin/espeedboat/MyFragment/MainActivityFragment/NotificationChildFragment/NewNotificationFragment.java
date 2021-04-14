package id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationChildFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import id.alin.espeedboat.MyAdapter.NotificationAdapter;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.R;

public class NewNotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationEntity> notifikasi;

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
                NotificationFragment notificationFragment = (NotificationFragment) getParentFragment();

                if(dy > 0){
                    notificationFragment.expandableLayout.collapse();
                }else if(dy < -200){
                    notificationFragment.expandableLayout.expand();
                }
            }
        });
        notifikasi = new ArrayList<>();
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(1);
        notificationEntity.setTitle("Transaksi Sukses !");
        notificationEntity.setMessage("Selamat transaksi anda telah berhasil dilakukan dan telah di verifikasi secara manual oleh admin sistem, silahkan segera persiapkan barang mu dan berangkat ke pelabuhan ! :)");
        notificationEntity.setNotification_by("admin");
        notificationEntity.setCreated_at("2020-01-01");
        notificationEntity.setStatus("normal");

        NotificationEntity notificationEntity2 = new NotificationEntity();
        notificationEntity2.setId(1);
        notificationEntity2.setTitle("Transaksi Sukses 1");
        notificationEntity2.setMessage("Selamat transaksi anda telah berhasil dilakukan dan telah di verifikasi secara manual oleh admin sistem, silahkan segera persiapkan barang mu dan berangkat ke pelabuhan ! :)");
        notificationEntity2.setNotification_by("admin");
        notificationEntity2.setCreated_at("2020-01-01");
        notificationEntity2.setStatus("normal");

        notifikasi.add(notificationEntity2);
        notifikasi.add(notificationEntity);
        notifikasi.add(notificationEntity);
        notifikasi.add(notificationEntity);
        notifikasi.add(notificationEntity);
        notifikasi.add(notificationEntity);


        notificationAdapter = new NotificationAdapter(notifikasi, getContext());
        recyclerView.setAdapter(notificationAdapter);
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