package me.zhang.workbench.adapter.viewPagerList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 12/31/2016 10:02 AM.
 */
public class StarVideoFragment extends Fragment {

    public static final String VIDEO = "video";

    public static StarVideoFragment newInstance(MetaData video) {

        Bundle args = new Bundle();
        args.putParcelable(VIDEO, video);
        StarVideoFragment fragment = new StarVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_star_video, container, false);

        TextView videoNameText = (TextView) rootView.findViewById(R.id.videoName);
        MetaData video = getArguments().getParcelable(VIDEO);
        if (video != null) {
            videoNameText.setText(video.name);
        }

        return rootView;
    }
}
