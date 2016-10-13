package me.zhang.workbench.adapter.contract;

import android.view.View;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public interface Visitable {

    int type(TypeFactory factory);

    BaseViewHolder holder(View itemView, HolderFactory factory);

}
