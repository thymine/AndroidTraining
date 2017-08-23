package me.zhang.workbench.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE;

/**
 * Created by Zhang on 8/19/2017 6:52 PM.
 */
public class CustomViewFragment extends Fragment {

    private static final int DELAY_MILLIS = 100;
    private static final int WHAT_PROGRESS = -1000;
    private static final int PROGRESS_MAX = 100;
    private static final int PROGRESS_MIN = 0;

    public static CustomViewFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE, title);

        CustomViewFragment fragment = new CustomViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.text_progress_bar)
    TextProgressBar mTextProgressBar;

    @BindView(R.id.progress_seek_bar)
    SeekBar mProgressSeekBar;

    private int mProgress;
    private boolean mIsAdd;
    private boolean mIsAutoProgress;

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROGRESS:
                    setProgress();
                    mHandler.sendEmptyMessageDelayed(WHAT_PROGRESS, DELAY_MILLIS);
                    return true;
            }
            return false;
        }
    };

    private void setProgress() {
        if (mProgress >= PROGRESS_MAX) {
            mIsAdd = false;
        } else if (mProgress <= PROGRESS_MIN) {
            mIsAdd = true;
        }
        if (mIsAdd) {
            realSetProgress(++mProgress);
        } else {
            realSetProgress(--mProgress);
        }
    }

    private void realSetProgress(int progress) {
        mTextProgressBar.setProgress(progress);
    }

    private Handler mHandler = new Handler(mCallback);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_view, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsAutoProgress) {
                    stopAutoProgressing();
                } else {
                    startAutoProgressing();
                }
            }
        });
        mProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                realSetProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopAutoProgressing();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void startAutoProgressing() {
        mIsAutoProgress = true;
        mHandler.sendEmptyMessageDelayed(WHAT_PROGRESS, DELAY_MILLIS);
    }

    private void stopAutoProgressing() {
        mIsAutoProgress = false;
        mHandler.removeMessages(WHAT_PROGRESS);
    }

}
