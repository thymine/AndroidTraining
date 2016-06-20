package me.zhang.art;

import me.zhang.art.activity.ActivityActivity;
import me.zhang.art.base.MenuActivity;
import me.zhang.art.ipc.IpcActivity;
import me.zhang.art.ipc.parcel.BinderPoolActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Activity", ActivityActivity.class);
        addMenuItem("Ipc", IpcActivity.class);
        addMenuItem("Binder Pool", BinderPoolActivity.class);
    }

}
