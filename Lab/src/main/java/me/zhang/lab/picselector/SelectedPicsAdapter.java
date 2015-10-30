package me.zhang.lab.picselector;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/30 下午 2:14 .
 */
public class SelectedPicsAdapter extends BaseAdapter {

    private static final String TAG = "SelectedPicsAdapter";
    private final Context context;
    private final List<ImageItem> imageItemList;
    private final OnAddButtonClickListener listener;

    public SelectedPicsAdapter(Context context, List<ImageItem> imageItemList, OnAddButtonClickListener listener) {
        this.context = context;
        this.imageItemList = imageItemList;
        this.listener = listener;

        ImageItem addItem = new ImageItem();
        addItem.path = Uri.EMPTY;
        this.imageItemList.add(addItem);
    }

    @Override
    public int getCount() {
        return imageItemList.size();
    }

    @Override
    public ImageItem getItem(int position) {
        return imageItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        ImageItem item = getItem(position);
        if (Uri.EMPTY.equals(item.path)) {
            imageView.setImageResource(R.drawable.image_add);
        } else {
            Picasso.with(context).load(item.path).into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == imageItemList.size() - 1) {
                    listener.onAddButtonClick();
                }
            }
        });

        return imageView;
    }

    public interface OnAddButtonClickListener {
        void onAddButtonClick();
    }

}
