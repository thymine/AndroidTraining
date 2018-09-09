package me.zhang.art.ipc.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/18/2016 11:32 AM.
 */
public class ProviderExternalUserActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCurrentPage;
    private ExternalAdapter mExternalAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mCurrentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setAdapter(mExternalAdapter = new ExternalAdapter(getSupportFragmentManager()));
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onClick(View v) {
        String currentTag = mExternalAdapter.fragmentTagArray.get(mCurrentPage);
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentTag);
        if (currentFragment != null && currentFragment instanceof BaseFragment)
            ((BaseFragment) currentFragment).createNew();
    }
}
