package me.zhang.laboratory.ui;

import me.zhang.laboratory.ui.base.MenuActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Corner Layout", CornerActivity.class);
        addMenuItem("Scroller", ScrollerActivity.class);
        addMenuItem("Coordinator", CoordinatorActivity.class);
        addMenuItem("Multi Launcher", MultiLauncherActivity.class);
        addMenuItem("Touch Event", TouchEventActivity.class);
        addMenuItem("Multitouch", MultitouchActivity.class);
        addMenuItem("Rotary Knob", RotaryKnobActivity.class);
    }

}
