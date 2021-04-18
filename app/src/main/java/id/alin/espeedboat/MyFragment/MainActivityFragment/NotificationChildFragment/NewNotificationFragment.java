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
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import id.alin.espeedboat.MyAdapter.NotificationAdapter;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.R;
import io.perfmark.Link;

public class NewNotificationFragment extends Fragment implements LifecycleOwner {

    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    private List<NotificationEntity> notificationEntities;

    // LAYOUT
    private LinearLayout content, nodatalayout;

    // DATABASE SISTEM
    private DatabaeESpeedboat databaeESpeedboat;

    public NewNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // INIT DATABASE
        databaeESpeedboat = DatabaeESpeedboat.createDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        initViewModel();
    }

    // INISIASI MENGHIDUPKAN OBSERVER KE VIEW MODEL NOTIFICATION FRAGMENT PARENT
    private void initViewModel() {
        NotificationFragment.notificationViewModel.getAllNotification().observe(this, new Observer<List<NotificationEntity>>() {
            @Override
            public void onChanged(List<NotificationEntity> notificationEntities) {

                    NewNotificationFragment.this.notificationEntities.clear();

                    notificationEntities.forEach(new Consumer<NotificationEntity>() {
                        @Override
                        public void accept(NotificationEntity notificationEntity) {
                            if(notificationEntity.getStatus() == 0){
                                NewNotificationFragment.this.notificationEntities.add(notificationEntity);
                            }
                        }
                    });
                    fillRecyclerView(NewNotificationFragment.this.notificationEntities);
            }
        });
    }

    // INISIASI SEMUA WIDGET
    private void init(View view){
        // LAYOUT
        this.content = view.findViewById(R.id.content);
        this.nodatalayout = view.findViewById(R.id.nodatalayout);
        this.notificationEntities = new LinkedList<>();
        // RECYCLER VIEW
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fillRecyclerView(this.notificationEntities);
    }

    // RECYCLERVIEW FILLER
    private void fillRecyclerView(List<NotificationEntity> notificationEntities) {
        try{
            if(notificationEntities.size() != 0){
                if(this.notificationAdapter == null){
                    this.notificationAdapter = new NotificationAdapter(notificationEntities,getContext());
                    this.recyclerView.setAdapter(this.notificationAdapter);
                    Log.d("debuginku","masuk sini 1");
                }else{
                    this.notificationAdapter.notificationentities = notificationEntities;
                    this.notificationAdapter.notifyDataSetChanged();
                    Log.d("debuginku","masuk sini 2");
                }
                setStateReady();
            }
        }catch(Exception ignored){
            setStateNoData();
        }
    }

    // SET STATE NO DATA
    private void setStateNoData(){
        this.nodatalayout.setVisibility(View.VISIBLE);
        this.content.setVisibility(View.INVISIBLE);
    }

    // SET STATE READY
    private void setStateReady(){
        this.nodatalayout.setVisibility(View.INVISIBLE);
        this.content.setVisibility(View.VISIBLE);
    }
}