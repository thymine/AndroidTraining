package me.zhang.lab.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/20 下午 2:27 .
 */
public class ExampleTopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_example, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 跳转到非UI用途的Fragment示例页面 */
                startActivity(new Intent(getActivity(), FragmentRetainInstanceActivity.class));
            }
        });
    }
}
