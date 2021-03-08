package id.alin.espeedboat.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.NotificationAdapter;
import id.alin.espeedboat.R;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    ArrayList<String> notifikasi;
    ArrayList<String> waktu;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerViewNotification);

        notifikasi = new ArrayList<>();
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");
        notifikasi.add("Pembayaranmu telah berhasil dilakukan! Klik untuk melihat status transaksi sekarang!");

        waktu = new ArrayList<>();
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");
        waktu.add("13 Januari 2021 13:00");

        notificationAdapter = new NotificationAdapter(notifikasi, waktu, getContext());
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