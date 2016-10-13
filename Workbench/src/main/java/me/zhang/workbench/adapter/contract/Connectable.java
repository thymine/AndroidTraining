package me.zhang.workbench.adapter.contract;

import android.view.View;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public interface Connectable<T> {

    void connect(View itemView, T data);

}
