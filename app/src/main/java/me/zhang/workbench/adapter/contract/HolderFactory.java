package me.zhang.workbench.adapter.contract;

import android.view.View;

import me.zhang.workbench.adapter.model.Car;
import me.zhang.workbench.adapter.model.Dog;
import me.zhang.workbench.adapter.model.Duck;
import me.zhang.workbench.adapter.model.Rat;
import me.zhang.workbench.adapter.holder.CarViewHolder;
import me.zhang.workbench.adapter.holder.DogViewHolder;
import me.zhang.workbench.adapter.holder.DuckViewHolder;
import me.zhang.workbench.adapter.holder.RatViewHolder;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public interface HolderFactory {

    DuckViewHolder holder(View itemView, Duck duck);

    DogViewHolder holder(View itemView, Dog dog);

    RatViewHolder holder(View itemView, Rat rat);

    CarViewHolder holder(View itemView, Car car);

}
