package me.zhang.laboratory.ui;

import me.zhang.laboratory.ui.base.MenuActivity;
import me.zhang.laboratory.ui.mediastore.QueryMediaCollectionActivity;

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
        addMenuItem("Custom EditText", CustomEditTextActivity.class);
        addMenuItem("Tabbed", TabbedActivity.class);
        addMenuItem("View to File", CaptureViewActivity.class);
        addMenuItem("Telephony", TelephonyActivity.class);
        addMenuItem("Pick File", PickFileActivity.class);
        addMenuItem("SurfaceView", SurfaceViewTestActivity.class);
        addMenuItem("RecordProgressBar", RecordProgressBarActivity.class);
        addMenuItem("Media Collection", QueryMediaCollectionActivity.class);
        addMenuItem("android.graphics.Camera", AndroidGraphicsCameraActivity.class);
    }

}
