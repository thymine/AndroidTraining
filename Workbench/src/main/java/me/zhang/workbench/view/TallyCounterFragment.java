package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.workbench.R;

import static me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE;

/**
 * Created by Zhang on 8/19/2017 6:41 PM.
 */
public class TallyCounterFragment extends Fragment {

    @BindView(R.id.button_click)
    Button mClick;

    @BindView(R.id.tally_counter)
    TallyCounterView mTallyCounter;

    @BindView(R.id.button_reset)
    Button mReset;

    private int mCount;

    public static TallyCounterFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE, title);

        TallyCounterFragment fragment = new TallyCounterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tally_counter, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.button_click)
    public void click(View view) {
        mTallyCounter.setCount(++mCount);
    }

    @OnClick(R.id.button_reset)
    public void reset(View view) {
        mTallyCounter.reset();
    }

}
