package me.zhang.art.activity;

import me.zhang.art.activity.lifecycle.LifecycleActivity;
import me.zhang.art.activity.singleInstance.SingleInstanceActivity;
import me.zhang.art.activity.singleTask.SingleTaskActivity;
import me.zhang.art.activity.singleTop.SingleTopActivity;
import me.zhang.art.activity.standard.StandardActivity;
import me.zhang.art.base.MenuActivity;

/**
 * Created by Li on 6/5/2016 1:33 PM.
 */
public class ActivityActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("Lifecycle", LifecycleActivity.class);
        addMenuItem("Standard", StandardActivity.class);
        addMenuItem("SingleTop", SingleTopActivity.class);
        addMenuItem("SingleTask", SingleTaskActivity.class);
        addMenuItem("SingleInstance", SingleInstanceActivity.class);
    }
}
