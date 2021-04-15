package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.LinkedList;
import java.util.List;

import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationChildFragment.NewNotificationFragment;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModel;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModelFactory;
import id.alin.espeedboat.R;

public class NotificationFragment extends Fragment implements LifecycleOwner {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public ExpandableLayout expandableLayout;

    // DATABASE
    private DatabaeESpeedboat databaeESpeedboat;

    // VIEW MODEL
    public static NotificationViewModel notificationViewModel;

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
        sectionpagerAdapter.addFragments(new NewNotificationFragment(), "NEW");
        sectionpagerAdapter.addFragments(new NewNotificationFragment(), "HISTORY");

        // MEMASUKKAN ADAPTER KE DALAM VIEWPAGER
        this.viewPager.setAdapter(sectionpagerAdapter);

        // MEMASUKKAN TABLAYOUT
        this.tabLayout.setupWithViewPager(this.viewPager);
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
