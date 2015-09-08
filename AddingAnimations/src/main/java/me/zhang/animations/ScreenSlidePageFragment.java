package me.zhang.animations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Zhang on 2015/5/11 下午 3:13 .
 */
public class ScreenSlidePageFragment extends Fragment {

    TextView mTextPage;

    private static final String CURRENT_POSITION = "CurrentPosition";

    public static ScreenSlidePageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(CURRENT_POSITION, position);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        mTextPage = (TextView) rootView.findViewById(R.id.text_page);

        Bundle args = getArguments();
        int currentPage = args.getInt(CURRENT_POSITION);
        mTextPage.setText(String.valueOf(currentPage + 1));

        switch (currentPage) {
            case 0:
                rootView.setBackgroundColor(Color.RED);
                break;
            case 1:
                rootView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                rootView.setBackgroundColor(Color.BLUE);
                break;
            case 3:
                rootView.setBackgroundColor(Color.YELLOW);
                break;
            case 4:
                rootView.setBackgroundColor(Color.CYAN);
                break;
        }

        return rootView;
    }

}
