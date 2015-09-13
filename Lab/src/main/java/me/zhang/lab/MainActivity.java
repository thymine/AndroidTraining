package me.zhang.lab;

import me.zhang.lab.propertyanimation.PropertyAnimatedActivity;
import me.zhang.lab.recyclerview.HorizontalListActivity;

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
        addMenuItem("RecyclerView Horizontal", HorizontalListActivity.class);
        addMenuItem("Property Animation", PropertyAnimatedActivity.class);
    }
}
