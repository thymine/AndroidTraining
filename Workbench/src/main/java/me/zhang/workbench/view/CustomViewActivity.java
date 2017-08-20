package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

/**
 * Created by Zhang on 2016/7/4 上午 9:41 .
 */
public class CustomViewActivity extends AppCompatActivity {

    // Custom view pages' title.
    public static final String FRAGMENT_TITLE = "fragment_title";

    @BindView(R.id.custom_views_pager)
    ViewPager mCustomViewsPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        List<Fragment> pageTitles = new ArrayList<>();
        pageTitles.add(TallyCounterFragment.newInstance(getString(R.string.fragment_title_tally_counter)));
        pageTitles.add(CustomViewFragment.newInstance(getString(R.string.fragment_title_custom_view)));
        mCustomViewsPager
                .setAdapter(new CustomViewsPagerAdapter(getSupportFragmentManager(), pageTitles));
    }

    private static class CustomViewsPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mCustomViewPages;

        public CustomViewsPagerAdapter(FragmentManager fm, List<Fragment> pageTitles) {
            super(fm);
            mCustomViewPages = pageTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return mCustomViewPages.get(position);
        }

        @Override
        public int getCount() {
            return mCustomViewPages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Fragment fragment = mCustomViewPages.get(position);
            Bundle args = fragment.getArguments();
            return args.getString(FRAGMENT_TITLE);
        }
    }

}
