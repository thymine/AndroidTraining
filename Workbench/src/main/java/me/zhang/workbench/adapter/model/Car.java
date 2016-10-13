package me.zhang.workbench.adapter.model;

import android.view.View;

import me.zhang.workbench.adapter.contract.BaseViewHolder;
import me.zhang.workbench.adapter.contract.HolderFactory;
import me.zhang.workbench.adapter.contract.TypeFactory;
import me.zhang.workbench.adapter.contract.Vehicle;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class Car extends Vehicle {

    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }

    @Override
    public BaseViewHolder holder(View itemView, HolderFactory factory) {
        return factory.holder(itemView, this);
    }

    @Override
    public String makeNoise() {
        return "rev";
    }

}
