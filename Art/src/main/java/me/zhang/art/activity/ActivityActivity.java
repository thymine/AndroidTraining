package me.zhang.art.activity;

import me.zhang.art.base.MenuActivity;

/**
 * Created by Li on 6/5/2016 1:33 PM.
 */
public class ActivityActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("Lifecycle", LifecycleActivity.class);
    }
}
