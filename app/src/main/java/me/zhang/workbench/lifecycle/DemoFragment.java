package me.zhang.workbench.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2016/11/15.
 */
public class DemoFragment extends Fragment {

    private static final String THIS_FRAGMENT = "fragment_tag";
    private static final String FRAGMENT_BACKGROUND = "fragment_background";
    private static final String TAG = DemoFragment.class.getSimpleName();

    private int mBackgroundColor;
    private int mFragmentTag;

    public static Fragment newInstance(int fragmentTag, int fragmentBackground) {
        Fragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putInt(THIS_FRAGMENT, fragmentTag);
        args.putInt(FRAGMENT_BACKGROUND, fragmentBackground);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle args = getArguments();
        mFragmentTag = args.getInt(THIS_FRAGMENT);
        mBackgroundColor = args.getInt(FRAGMENT_BACKGROUND);

        Log.i(TAG, "onAttach: " + mFragmentTag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: " + mFragmentTag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: " + mFragmentTag);

        View rootView = inflater.inflate(R.layout.fragment_lifecycle_inside_view_pager, container, false);
        rootView.setBackgroundColor(mBackgroundColor);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: " + mFragmentTag);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: " + mFragmentTag);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + mFragmentTag);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: " + mFragmentTag);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: " + mFragmentTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: " + mFragmentTag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: " + mFragmentTag);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: " + mFragmentTag);
    }

}
