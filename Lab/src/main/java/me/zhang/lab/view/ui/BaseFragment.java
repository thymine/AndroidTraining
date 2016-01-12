package me.zhang.lab.view.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zhang on 2016/1/12 下午 3:01 .
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(supplyLayoutResource(), container, false);
    }

    protected abstract int supplyLayoutResource();
}
