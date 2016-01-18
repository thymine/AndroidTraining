package me.zhang.lab.view.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import me.zhang.lab.R;

public class CustomViewActivity extends AppCompatActivity {

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments.add(PulsationViewFragment.newInstance());
        fragments.add(BitmapViewFragment.newInstance());
        fragments.add(PorterDuffViewFragment.newInstance());
        fragments.add(DisInViewFragment.newInstance());
        fragments.add(DisOutViewFragment.newInstance());
        fragments.add(ScreenViewFragment.newInstance());
        fragments.add(EraserViewFragment.newInstance());
        fragments.add(FontViewFragment.newInstance());
        fragments.add(StaticLayoutViewFragment.newInstance());
        fragments.add(MaskFilterViewFragment.newInstance());
        fragments.add(BlurMaskFilterViewFragment.newInstance());
        fragments.add(EmbossMaskFilterViewFragment.newInstance());
        fragments.add(PathEffectViewFragment.newInstance());
        fragments.add(ECGViewFragment.newInstance());
        fragments.add(ShadowViewFragment.newInstance());
        fragments.add(ShaderViewFragment.newInstance());
        fragments.add(BrickViewFragment.newInstance());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
          The {@link android.support.v4.view.PagerAdapter} that will provide
          fragments for each of the sections. We use a
          {@link FragmentPagerAdapter} derivative, which will keep every
          loaded fragment in memory. If this becomes too memory intensive, it
          may be best to switch to a
          {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
          The {@link ViewPager} that will host the section contents.
         */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(fragments.size() - 1);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
