package me.zhang.workbench.adapter.contract;

import me.zhang.workbench.adapter.model.Car;
import me.zhang.workbench.adapter.model.Dog;
import me.zhang.workbench.adapter.model.Duck;
import me.zhang.workbench.adapter.model.Rat;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public interface TypeFactory {

    int type(Duck duck);

    int type(Dog dog);

    int type(Rat rat);

    int type(Car car);

}
