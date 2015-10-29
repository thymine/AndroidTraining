package me.zhang.lab.picselector;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/29 下午 5:19 .
 */
public class PostFragment extends BaseFragment {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_post;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // The view was created, do what you want
    }

    public static Fragment newInstance(Bundle args) {
        Fragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
