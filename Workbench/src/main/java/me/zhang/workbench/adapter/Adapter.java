package me.zhang.workbench.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2016/10/12.
 */
public class Adapter extends RecyclerView.Adapter {

    private static final int TYPE_CAR = 0;
    private static final int TYPE_DUCK = 1;
    private static final int TYPE_MOUSE = 2;
    private static final int TYPE_DOG = 3;
    private List<String> mStringList;
    private LayoutInflater mInflater;

    public Adapter(List<String> mStringList) {
        this.mStringList = mStringList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_CAR:
                mInflater.inflate(R.layout.item_car, parent, false);
                break;
            case TYPE_DUCK:
                break;
            case TYPE_DOG:
                break;
            case TYPE_MOUSE:
                break;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return TYPE_CAR;
        } else if (position % 5 == 0) {
            return TYPE_DOG;
        } else if (position % 7 == 0) {
            return TYPE_DUCK;
        } else {
            return TYPE_MOUSE;
        }
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }
}
