package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationChildFragment.HistoryNotificationFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationChildFragment.NewNotificationFragment;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Notification.ServerResponseNotificationData;
import id.alin.espeedboat.MyRetrofit.Services.NotificationServices;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.MySharedPref.Config;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModel;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModelFactory;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment implements LifecycleOwner {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public ExpandableLayout expandableLayout;

    // SHARED PREFERENCES
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // DATABASE
    private DatabaeESpeedboat databaeESpeedboat;

    // VIEW MODEL
    public static NotificationViewModel notificationViewModel;

    // CLOCK PEMBATAS PEMANGGILAN API NOTIFICATION
    private long mLastClickTime = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidget();
        initrSharedPreferences();
    }

    // INISIASI SHARED PREFERENCES
    private void initrSharedPreferences() {
        this.sharedPreferences = getContext().getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
    }

    // INIT VIEW MODEL
    private void initViewModel() {
        // INI VIEW MODEL YANG AKAN DIGUNAKAN DISETIAP CHILD FRAGMENT
        if (notificationViewModel == null) {
            notificationViewModel = new ViewModelProvider(this, new NotificationViewModelFactory())
                    .get(NotificationViewModel.class);
        }

        // FILL VIEW MODEL DENGAN DATA DARI ROOM
        databaeESpeedboat = DatabaeESpeedboat.createDatabase(getContext());
        notificationViewModel.setNotificationData(databaeESpeedboat.notificationDAO().getAllNotificationEntity());
    }

    // METHOD UNTUK MELAKUKAN INISIASI WIDGET
    private void initWidget() {
        // INISIASI VIEW PAGER DAN TAB LAYOUT
        this.viewPager = getView().findViewById(R.id.viewpager);
        this.tabLayout = getView().findViewById(R.id.tablayout);
        this.expandableLayout = getView().findViewById(R.id.expand_tablayout);

        // MEMBUAT SECTIONPAGER ADAPTER YANG AKAN DIMASUKKAN KE DALAM VIEWPAGER
        SectionpagerAdapter sectionpagerAdapter = new SectionpagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        sectionpagerAdapter.addFragments(new NewNotificationFragment(), "BARU");
        sectionpagerAdapter.addFragments(new HistoryNotificationFragment(), "ARSIP");

        // MEMASUKKAN ADAPTER KE DALAM VIEWPAGER
        this.viewPager.setAdapter(sectionpagerAdapter);

        // MEMASUKKAN TABLAYOUT
        this.tabLayout.setupWithViewPager(this.viewPager);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // AMBIL STATUS AUTO_LAUNCH TERAKHIR DARI SHARED PREFERENCES
            int status_auto_launch = sharedPreferences.getInt(Config.Setting.AUTO_LAUNCH, 0);

            // APABILA STATUS NYA 0 BERARTI USER BELUM MELAKUKAN PERUBAHAN TERHADAP AUTO LAUNCH
            if (status_auto_launch == 0) {
                showAskAutoLaunch();
            }


            // READ NOTIFIKASI TERBARU DARI SERVER APABILA ADA YANG TERLEWA
            if (SystemClock.elapsedRealtime() - mLastClickTime > 10000) {
                mLastClickTime = SystemClock.elapsedRealtime();
                ProfileData profileData = MainActivity.mainActivityViewModel.getProfileLiveData().getValue();
                getNotificationFromAPI(profileData.getToken());
            }
        }
    }

    // METHO DUNTUK MENDAPATKAN TOKEN DARI API
    private void getNotificationFromAPI(String token) {

        // PANGGIL SERVICE NOTIFICAITON
        NotificationServices notificationServices = ApiClient.getRetrofit().create(NotificationServices.class);

        Call<ServerResponseNotificationData> call = notificationServices.getAllNotification(token);

        call.enqueue(new Callback<ServerResponseNotificationData>() {
            @Override
            public void onResponse(Call<ServerResponseNotificationData> call, Response<ServerResponseNotificationData> response) {
                try {
                if (response.body().getStatus().matches("success") && response.body().getResponse_code().matches("200")) {
                    if (databaeESpeedboat != null) {
                        assert response.body() != null;
                        List<NotificationEntity> notificationEntities_response = response.body().getNotifications();

                        // LOOP UNTUK UPDATE ATAU LANGSUNG INSERT KE DALAM DATABASE
                        notificationEntities_response.forEach(new Consumer<NotificationEntity>() {
                            @Override
                            public void accept(NotificationEntity notificationEntity) {
                                // APABILA NOTIFICATION SUDAH ADA DI DALAM DATABASE MAKA AKAN DIUPDATE SAJA
                                if (databaeESpeedboat.notificationDAO().getNotificationEntityById(notificationEntity.getId_server_notification()) != null) {
                                    NotificationEntity editable_notification = databaeESpeedboat.notificationDAO().getNotificationEntityById(notificationEntity.getId_server_notification());
                                    editable_notification.setMessage(notificationEntity.getMessage());
                                    editable_notification.setTitle(notificationEntity.getTitle());
                                    editable_notification.setType(notificationEntity.getType());
                                    databaeESpeedboat.notificationDAO().updateNotification(editable_notification);
                                } else {
                                    databaeESpeedboat.notificationDAO().insertNotification(notificationEntity);
                                }
                            }
                        });

                        // UPDATE NOTIFICAITON FRAGMENT VIEWMODEL
                        NotificationFragment.notificationViewModel.setNotificationData(databaeESpeedboat.notificationDAO().getAllNotificationEntity());
                    }
                }
                } catch (Exception ignored) {
                    Toast.makeText(getContext(), "CANT GET NOTIFICATION FROM SERVER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseNotificationData> call, Throwable t) {

            }
        });
    }

// ASK FOR PERMISSION AUTO START PERMISSION ON VIVO DEVICE
// AUTO START DIPERLUKAN UNTUK MENDAPATKAN NOTIFICATION SAAT APP DI KILL

    // METHOD UNTUK MENGAKTIFKAN AUTO LAUNCH
    private void goToAutoLaunchSetting() {
        // INTENT KE DALAM SISTEM AUTO LAUNCH BERDASARKAN PADA DEVICE YANG DIGUNAKAN
        if (Build.BRAND.equalsIgnoreCase("vivo")) {
            autoLaunchVivo(getContext());
        } else if (Build.BRAND.equalsIgnoreCase("oppo")) {
            autoLaunchOppo(getContext());
        } else if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            autoLaunchXiaomi(getContext());
        } else if (Build.BRAND.equalsIgnoreCase("Letv")) {
            autoLaunchLetv(getContext());
        } else if (Build.BRAND.equalsIgnoreCase("Honor")) {
            autoLaunchHonor(getContext());
        } else {
            showUnknownDevice();
        }
    }

    // AUTO START UNTUK VIVO
    private void autoLaunchVivo(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.iqoo.secure",
                    "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                context.startActivity(intent);
            } catch (Exception ex) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.iqoo.secure",
                            "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                    context.startActivity(intent);
                } catch (Exception exx) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // AUTO START UNTUK OPPO
    private void autoLaunchOppo(Context context) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.coloros.safecenter",
                    "com.coloros.safecenter.permission.startup.StartupAppListActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.oppo.safe",
                        "com.oppo.safe.permission.startup.StartupAppListActivity");
                context.startActivity(intent);

            } catch (Exception ex) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.coloros.safecenter",
                            "com.coloros.safecenter.startupapp.StartupAppListActivity");
                    context.startActivity(intent);
                } catch (Exception exx) {
                    exx.printStackTrace();
                }
            }
        }
    }

    // AUTO START UNTUK XIAOMI
    private void autoLaunchXiaomi(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AUTO START UNTUK LETV
    private void autoLaunchLetv(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AUTO START UNTUK HONOR
    private void autoLaunchHonor(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // METHOD YANG AKAN DIGUNAKAN UNTUK MENAMPILKAN BAGAIMANA
    private void showAskAutoLaunch() {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.parentlayoutMainActivity), "Untuk menerima notifikasi secara real time mohon aktifkan auto launch sistem", 10000);
        snackbar.setAction("OKE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                goToAutoLaunchSetting();

                // GANTI STATUS DARI AUTO_LAUNCH
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        editor = sharedPreferences.edit();
                        editor.putInt(Config.Setting.AUTO_LAUNCH, 1);
                        editor.apply();
                    }
                });
            }
        })
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.white))
                .setAnchorView(R.id.botnavbarMainActivity)
                .setBackgroundTint(ContextCompat.getColor(getContext(), R.color.Blue_primary))
                .setGestureInsetBottomIgnored(true)
                .show();
    }

    // UNKNOWN DEVICE SPARKBAR REPORT
    private void showUnknownDevice() {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.parentlayoutMainActivity), "Untuk mendapatkan real time notidication mohon aktifkan auto launch aplikasi pada pengaturan sistem anda", 3000);
        snackbar.setAction("OKE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();

                // GANTI STATUS DARI AUTO_LAUNCH
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        editor = sharedPreferences.edit();
                        editor.putInt(Config.Setting.AUTO_LAUNCH, 1);
                        editor.apply();
                    }
                });
            }
        })
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.white))
                .setAnchorView(R.id.botnavbarMainActivity)
                .setBackgroundTint(ContextCompat.getColor(getContext(), R.color.Blue_primary))
                .show();
    }
}

// CLASS UNTUK MELAKUKAN PEMSANANGAN FRAGMENT SPEEDBOAT
class SectionpagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new LinkedList<>();
    private final List<String> titles = new LinkedList<>();

    public SectionpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    void addFragments(Fragment fragment, String title) {
        this.fragments.add(fragment);
        this.titles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
