package me.zhang.bmps.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import me.zhang.bmps.R;

/**
 * Created by zhang on 15-5-3 上午11:13.
 */
public class ImageDetailActivity extends FragmentActivity {

    public static final String EXTRA_IMAGE = "extra_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(
                getSupportFragmentManager(),
                CachingActivity.mDatas.size()
        );
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        // Set the current item based on the extra passed in to this activity
        final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, 0);
        if (extraCurrentItem != -1) {
            pager.setCurrentItem(extraCurrentItem);
        }

    }

    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mSize;
        }
    }

}
