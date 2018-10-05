package me.zhang.workbench.ui;

import me.zhang.workbench.ui.base.MenuActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Corner Layout", CornerActivity.class);
        addMenuItem("Scroller", ScrollerActivity.class);
    }

}
