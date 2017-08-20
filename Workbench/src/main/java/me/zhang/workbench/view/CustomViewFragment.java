package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.workbench.R;

import static me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE;

/**
 * Created by Zhang on 8/19/2017 6:52 PM.
 */
public class CustomViewFragment extends Fragment {

    public static CustomViewFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE, title);

        CustomViewFragment fragment = new CustomViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_view, container, false);
    }

}
