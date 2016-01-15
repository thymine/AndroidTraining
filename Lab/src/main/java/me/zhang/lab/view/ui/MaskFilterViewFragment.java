package me.zhang.lab.view.ui;

import me.zhang.lab.R;


/**
 * Created by Zhang on 2015/12/24 上午 10:33 .
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class MaskFilterViewFragment extends BaseFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MaskFilterViewFragment newInstance() {
        return new MaskFilterViewFragment();
    }

    @Override
    protected int supplyLayoutResource() {
        return R.layout.fragment_mask_filter;
    }
}
