package me.zhang.workbench.lifecycle;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Created by zhangxiangdong on 2016/11/15.
 */
public class LifecyclePagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 5;

    private SparseIntArray mColors = new SparseIntArray();
    private SparseArray<String> mTitles = new SparseArray<>();

    {
        mColors.put(0, Color.RED);
        mColors.put(1, Color.GREEN);
        mColors.put(2, Color.BLUE);
        mColors.put(3, Color.WHITE);
        mColors.put(4, Color.BLACK);

        mTitles.put(0, "Red");
        mTitles.put(1, "Green");
        mTitles.put(2, "Blue");
        mTitles.put(3, "White");
        mTitles.put(4, "Black");
    }

    public LifecyclePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DemoFragment.newInstance(position, mColors.get(position));
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
