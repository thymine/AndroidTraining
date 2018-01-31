package me.zhang.workbench.adapter;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import me.zhang.workbench.R;
import me.zhang.workbench.design.image.widget.ThreeTwoImageView;

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
        View itemView = mLayoutInflater.inflate(R.layout.item_recycler, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setImage(mImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ThreeTwoImageView mRatioImage;
        String mImageUrl;
        GestureDetectorCompat mDetectorCompat = new GestureDetectorCompat(mContext,
                new GestureDetector.SimpleOnGestureListener() {

                    public static final String LOG_TAG = "Holder";

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        // remove this image on image double clicked
                        String imageUrl = mImageList.get(getAdapterPosition());
                        Log.d(LOG_TAG, "mImageUrl: " + mImageUrl);
                        Log.d(LOG_TAG, "imageUrl: " + imageUrl);
                        mImageList.remove(imageUrl);
                        notifyItemRemoved(getAdapterPosition());
                        return true;
                    }
                });

        public void setImage(String imageUrl) {
            mImageUrl = imageUrl;
            doBind();
        }

        private void doBind() {
            Glide.with(mContext).load(mImageUrl).into(mRatioImage);
        }

        public Holder(View itemView) {
            super(itemView);
            mRatioImage = itemView.findViewById(R.id.ratioImage);
            mRatioImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mDetectorCompat.onTouchEvent(event);
                }
            });
        }

    }

}
