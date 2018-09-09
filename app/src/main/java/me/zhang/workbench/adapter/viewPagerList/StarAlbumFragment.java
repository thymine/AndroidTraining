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
public class StarAlbumFragment extends Fragment {

    public static final String ALBUM = "data";

    public static StarAlbumFragment newInstance(MetaData album) {

        Bundle args = new Bundle();
        args.putParcelable(ALBUM, album);
        StarAlbumFragment fragment = new StarAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_star_album, container, false);

        TextView albumNameText = (TextView) rootView.findViewById(R.id.albumName);
        MetaData album = getArguments().getParcelable(ALBUM);
        if (album != null) {
            albumNameText.setText(album.name);
        }

        return rootView;
    }
}
