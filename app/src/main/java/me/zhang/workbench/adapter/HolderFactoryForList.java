package me.zhang.workbench.adapter;

import android.view.View;

import me.zhang.workbench.adapter.contract.HolderFactory;
import me.zhang.workbench.adapter.holder.CarViewHolder;
import me.zhang.workbench.adapter.holder.DogViewHolder;
import me.zhang.workbench.adapter.holder.DuckViewHolder;
import me.zhang.workbench.adapter.holder.RatViewHolder;
import me.zhang.workbench.adapter.model.Car;
import me.zhang.workbench.adapter.model.Dog;
import me.zhang.workbench.adapter.model.Duck;
import me.zhang.workbench.adapter.model.Rat;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class HolderFactoryForList implements HolderFactory {

    @Override
    public DuckViewHolder holder(View itemView, Duck duck) {
        return new DuckViewHolder(itemView);
    }

    @Override
    public DogViewHolder holder(View itemView, Dog dog) {
        return new DogViewHolder(itemView);
    }

    @Override
    public RatViewHolder holder(View itemView, Rat rat) {
        return new RatViewHolder(itemView);
    }

    @Override
    public CarViewHolder holder(View itemView, Car car) {
        return new CarViewHolder(itemView);
    }
}
