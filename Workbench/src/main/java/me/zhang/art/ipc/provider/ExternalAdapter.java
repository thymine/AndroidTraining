package me.zhang.art.ipc.provider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Li on 6/18/2016 6:48 PM.
 */
public class ExternalAdapter extends FragmentPagerAdapter {

    public SparseArray<String> fragmentTagArray = new SparseArray<>();

    public ExternalAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // get the tags set by FragmentPagerAdapter and save them for future use
        switch (position) {
            case 0:
                String firstTag = createdFragment.getTag();
                fragmentTagArray.put(position, firstTag);
                break;
            case 1:
                String secondTag = createdFragment.getTag();
                fragmentTagArray.put(position, secondTag);
                break;
        }
        return createdFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = BookListFragment.newInstance();
                break;
            case 1:
                fragment = UserListFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = null;
        switch (position) {
            case 0:
                title = "Books";
                break;
            case 1:
                title = "Users";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
