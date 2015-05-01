package me.zhang.bmps.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.bmps.R;

public class CachingFragment extends Fragment {

    private static final String TAG = CachingFragment.class.getSimpleName();
    public LruCache<String, Bitmap> mRetainedCache;

    public CachingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cache, container, false);
    }

    public static CachingFragment findOrCreateCacheFragment(FragmentManager fm) {
        CachingFragment fragment = (CachingFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new CachingFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain the cache - mRetainedCache
        setRetainInstance(true);
    }
}
