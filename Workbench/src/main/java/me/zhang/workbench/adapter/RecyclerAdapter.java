package me.zhang.workbench.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2016/11/17.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final String TAG = "RecyclerAdapter";
    private Context mContext;
    private List<String> mImageList;
    private LayoutInflater mLayoutInflater;

    public RecyclerAdapter(Context context, List<String> imageList) {
        mContext = context;
        mImageList = imageList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View itemView = mLayoutInflater.inflate(R.layout.item_recycler, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i(TAG, "onBindViewHolder: position > " + position);
        Glide.with(mContext).load(mImageList.get(position)).into(holder.mRatioImage);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView mRatioImage;

        public Holder(View itemView) {
            super(itemView);
            mRatioImage = (ImageView) itemView.findViewById(R.id.ratioImage);
        }

    }

}
