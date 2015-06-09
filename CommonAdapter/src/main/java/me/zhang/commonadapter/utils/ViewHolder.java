package me.zhang.commonadapter.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zhang on 6/9/2015 3:31 下午.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    public ViewHolder(Context context, int layoutId, int position, ViewGroup parent) {
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        mPosition = position;
    }

    public static ViewHolder get(Context context, int layoutId, int position,
                                 View converView, ViewGroup parent) {
        if (converView == null) {
            return new ViewHolder(context, layoutId, position, parent);
        } else {
            ViewHolder holder = (ViewHolder) converView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
