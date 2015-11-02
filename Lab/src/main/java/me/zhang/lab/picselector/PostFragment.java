package me.zhang.lab.picselector;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/29 下午 5:19 .
 */
public class PostFragment extends BaseFragment {

    private static final String TAG = "PostFragment";
    private static final int RESULT_LOAD_IMAGE = 7;

    @Bind(R.id.et_content)
    EditText content;

    @Bind(R.id.vv_video)
    VideoView video;

    @Bind(R.id.gv_pics_selected)
    GridView selPictures;

    @Bind(R.id.iv_location)
    ImageView locationIcon;

    @Bind(R.id.tv_selected_location)
    TextView selLocation;

    private List<ImageItem> imageItemList = new ArrayList<>();
    private SelectedPicsAdapter selectedPicsAdapter;

    public static Fragment newInstance(Bundle args) {
        Fragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedPicsAdapter = new SelectedPicsAdapter(getActivity(), imageItemList);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_post;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        selPictures.setAdapter(selectedPicsAdapter);
        selPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pickImages(position);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        Log.i(TAG, "selectedImageUri: " + selectedImageUri);
                        ImageItem item = new ImageItem();
                        item.path = selectedImageUri;
                        addItemToTail(item);
                        selectedPicsAdapter.notifyDataSetChanged();
                    } else {
                        Log.i(TAG, "selectedImageUri == null");
                    }
                }
            }
        }
    }

    private void addItemToTail(ImageItem item) {
        int size = imageItemList.size();
        if (size > 0) {
            int pos = size - 1;
            imageItemList.add(pos, item);
        }
    }

    public void pickImages(int position) {
        if (position == imageItemList.size() - 1) { // Click the last image add icon
            // Jump to gallery and select multiple images
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
        } else {
            // Show image details
        }
    }

}
