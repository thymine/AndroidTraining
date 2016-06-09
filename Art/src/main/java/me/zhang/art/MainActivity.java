package me.zhang.art;

import me.zhang.art.activity.ActivityActivity;
import me.zhang.art.base.MenuActivity;
import me.zhang.art.ipc.IPCActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Activity", ActivityActivity.class);
        addMenuItem("IPC", IPCActivity.class);
    }

}
