package me.zhang.lab;

import me.zhang.lab.camera.CameraActivity;
import me.zhang.lab.fragment.ExampleActivity;
import me.zhang.lab.listview.ListViewActivity;
import me.zhang.lab.listview.NonScrollableListViewActivity;
import me.zhang.lab.propertyanimation.PropertyAnimatedActivity;
import me.zhang.lab.recyclerview.HorizontalListActivity;
import me.zhang.lab.timer.CountDownActivity;
import me.zhang.lab.timer.CountUpActivity;
import me.zhang.lab.base.MenuActivity;
import me.zhang.lab.videoview.MovingActivity;

/**
 * Created by Zhang on 2015/7/27 下午 4:30 .
 */
public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Fragment Example", ExampleActivity.class);
        addMenuItem("Count Down Timer", CountDownActivity.class);
        addMenuItem("Non-Scrollable ExpandableListView", NonScrollableListViewActivity.class);
        addMenuItem("Count Up Timer", CountUpActivity.class);
        addMenuItem("ListView", ListViewActivity.class);
        addMenuItem("Moving", MovingActivity.class);
        addMenuItem("RecyclerView Horizontal", HorizontalListActivity.class);
        addMenuItem("Property Animation", PropertyAnimatedActivity.class);
        addMenuItem("Camera", CameraActivity.class);
    }
}
