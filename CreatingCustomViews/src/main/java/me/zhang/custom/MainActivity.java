package me.zhang.custom;

/**
 * Created by Zhang on 8/15/2015 8:20 下午.
 */
public class MainActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("Pie Chart", PieActivity.class);
        addMenuItem("Lovely View", LovelyActivity.class);
    }
}
