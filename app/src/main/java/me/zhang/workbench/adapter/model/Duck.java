package me.zhang.workbench.adapter.model;

import android.view.View;

import me.zhang.workbench.adapter.contract.Animal;
import me.zhang.workbench.adapter.contract.BaseViewHolder;
import me.zhang.workbench.adapter.contract.HolderFactory;
import me.zhang.workbench.adapter.contract.TypeFactory;
import me.zhang.workbench.adapter.holder.DuckViewHolder;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class Duck extends Animal {
    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }

    @Override
    public BaseViewHolder holder(View itemView, HolderFactory factory) {
        return new DuckViewHolder(itemView);
    }

    @Override
    public String makeNoise() {
        return "quack";
    }
}
