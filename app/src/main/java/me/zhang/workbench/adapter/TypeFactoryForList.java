package me.zhang.workbench.adapter;

import me.zhang.workbench.R;
import me.zhang.workbench.adapter.contract.TypeFactory;
import me.zhang.workbench.adapter.model.Car;
import me.zhang.workbench.adapter.model.Dog;
import me.zhang.workbench.adapter.model.Duck;
import me.zhang.workbench.adapter.model.Rat;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class TypeFactoryForList implements TypeFactory {

    @Override
    public int type(Duck duck) {
        return R.layout.item_duck;
    }

    @Override
    public int type(Dog dog) {
        return R.layout.item_dog;
    }

    @Override
    public int type(Rat rat) {
        return R.layout.item_mouse;
    }

    @Override
    public int type(Car car) {
        return R.layout.item_car;
    }
}
