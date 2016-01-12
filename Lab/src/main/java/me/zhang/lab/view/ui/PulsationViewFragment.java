package me.zhang.lab.view.ui;

import android.os.Bundle;
import android.view.View;

import me.zhang.lab.R;
import me.zhang.lab.view.PulsationView;


/**
 * Created by Zhang on 2015/12/24 上午 10:33 .
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class PulsationViewFragment extends BaseFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PulsationViewFragment newInstance() {
        return new PulsationViewFragment();
    }

    @Override
    protected int supplyLayoutResource() {
        return R.layout.fragment_pulsation;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        PulsationView pulsationView = (PulsationView) view.findViewById(R.id.pulsation);
        new Thread(pulsationView).start();
    }
}
