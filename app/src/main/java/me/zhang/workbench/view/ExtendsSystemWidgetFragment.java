package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE;

/**
 * Created by zhangxiangdong on 2017/9/13.
 */
public class ExtendsSystemWidgetFragment extends Fragment {

    public static ExtendsSystemWidgetFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE, title);

        ExtendsSystemWidgetFragment fragment = new ExtendsSystemWidgetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extends_system_widget, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
