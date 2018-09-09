package me.zhang.workbench.adapter.model;

import android.view.View;

import me.zhang.workbench.adapter.contract.Animal;
import me.zhang.workbench.adapter.contract.BaseViewHolder;
import me.zhang.workbench.adapter.contract.HolderFactory;
import me.zhang.workbench.adapter.contract.TypeFactory;
import me.zhang.workbench.adapter.holder.RatViewHolder;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class Rat extends Animal {
    @Override
    public int type(TypeFactory factory) {
        return factory.type(this);
    }

    @Override
    public BaseViewHolder holder(View itemView, HolderFactory factory) {
        return new RatViewHolder(itemView);
    }

    @Override
    public String makeNoise() {
        return "squeak";
    }
}
