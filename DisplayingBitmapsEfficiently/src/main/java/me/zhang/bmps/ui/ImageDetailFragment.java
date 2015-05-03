package me.zhang.bmps.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.zhang.bmps.R;
import me.zhang.bmps.util.ImageCache;

/**
 * Created by zhang on 15-5-3 上午11:15.
 */
public class ImageDetailFragment extends Fragment {

    private static final String IMAGE_DATA_EXTRA = "position";
    private int mPosition;
    private ImageView mImageView;

    public ImageDetailFragment() {
    }

    static ImageDetailFragment newInstance(int position) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image_view);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof ImageDetailActivity) {
            ImageCache mImageCache = ImageCache.newInstance(getActivity());
            // load the bitmap in a background thread
            mImageCache.loadBitmap(CachingActivity.mDatas.get(mPosition), mImageView);
        }
    }
}
