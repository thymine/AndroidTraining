package me.zhang.lab.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.lab.R;


/**
 * Created by Zhang on 2015/12/24 上午 10:33 .
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class CustomViewFragment extends Fragment {

    private CustomView customView;

    public CustomViewFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CustomViewFragment newInstance() {
        return new CustomViewFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        customView = (CustomView) view.findViewById(R.id.custom);

        new Thread(customView).start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
