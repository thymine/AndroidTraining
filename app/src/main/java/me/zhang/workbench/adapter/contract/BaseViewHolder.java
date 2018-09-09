package me.zhang.workbench.adapter.contract;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements Bindable {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

}
