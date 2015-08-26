package com.soufun.lab;

/**
 * Created by Zhang on 2015/7/27 下午 4:30 .
 */
public class MainActivity extends MenuActivity {


    @Override
    protected void prepareMenu() {
        addMenuItem("Count Down Timer", CountDownActivity.class);
        addMenuItem("Non-Scrollable ExpandableListView", NonScrollableListViewActivity.class);
        addMenuItem("Count Up Timer", CountUpActivity.class);
        addMenuItem("ListView", ListViewActivity.class);
        addMenuItem("Moving", MovingActivity.class);
    }
}
