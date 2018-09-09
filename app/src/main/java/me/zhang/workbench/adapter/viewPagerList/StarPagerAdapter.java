package me.zhang.workbench.adapter.viewPagerList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Zhang on 12/31/2016 9:59 AM.
 */
public class StarPagerAdapter extends FragmentStatePagerAdapter {

    private List<Category> mCategoryList;

    public StarPagerAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        mCategoryList = categories;
    }

    @Override
    public Fragment getItem(int position) {
        Category category = mCategoryList.get(position);
        MetaData data = category.data;
        int type = category.categoryType;
        Fragment fragment;
        if (type == CategoryType.TYPE_ALBUM) {
            fragment = StarAlbumFragment.newInstance(data);
        } else {
            fragment = StarVideoFragment.newInstance(data);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        if (mCategoryList == null) {
            return 0;
        }
        return mCategoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Category category = mCategoryList.get(position);
        if (category != null) {
            return category.categoryName;
        }
        return "";
    }
}
