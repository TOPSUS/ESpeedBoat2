package id.alin.espeedboat.MyFragment.MainActivityFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.LinkedList;
import java.util.List;

import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FeriFragment;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.SpeedBoatFragment;
import id.alin.espeedboat.MyHelper.HeightWrappingViewPager;
import id.alin.espeedboat.PemesananJadwalActivity;
import id.alin.espeedboat.R;

public class PemesananJadwalFragment extends Fragment implements LifecycleOwner {

    /*WIDGET YANG DIGUNAKAN PADA LAMAN*/
    private MaterialEditText metasal;
    private MaterialEditText mettujuan;
    private MaterialEditText mettanggal;
    private MaterialEditText metjumlahpenumpang;
    private Button btncari;

    private HeightWrappingViewPager viewPager;
    private TabLayout tabLayout;

    /*FRAGMENT TAGS*/
    public static final String FRAGMENT_FULLSCREEN_ASAL = "FRAGMENT_FULLSCREEN_ASAL";
    public static final String FRAGMENT_FULLSCREEN_TUJUAN = "FRAGMENT_FULLSCREEN_TUJUAN";

    public PemesananJadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemesanan_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewPager = view.findViewById(R.id.viewpagerpemesanan);
        createViewPager();

        this.tabLayout = view.findViewById(R.id.tablayout);

        this.tabLayout.setupWithViewPager(this.viewPager);
    }

    /*PEMBUATAN VIEW PAGER*/
    private void createViewPager(){

        /*BUAT SECTIONPAGE ADAPTER*/
        SectionpagerAdapter sectionpagerAdapter = new SectionpagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        /*BUAT FRAGMENT SPEEDBOAT*/
        sectionpagerAdapter.addFragments(SpeedBoatFragment.newInstance(),"SPEEDBOAT");

        /*BUAT FRAGMENT FERI*/
        sectionpagerAdapter.addFragments(FeriFragment.newInstance(),"FERI");

        this.viewPager.setAdapter(sectionpagerAdapter);
    }

    /*CLASS UNTUK MELAKUKAN PEMSANANGAN FRAGMENT SPEEDBOAT*/
    private class SectionpagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new LinkedList<>();
        private final List<String> titles = new LinkedList<>();

        public SectionpagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        void addFragments(Fragment fragment, String title){
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
}