package me.zhang.art.ipc.provider;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Li on 6/18/2016 10:04 PM.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * create new item (book or user)
     */
    public abstract void createNew();

}
