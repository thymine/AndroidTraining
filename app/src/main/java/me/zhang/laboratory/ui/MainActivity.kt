package me.zhang.laboratory.ui

import me.zhang.laboratory.ui.base.MenuActivity
import me.zhang.laboratory.ui.compose.ComposableActivity
import me.zhang.laboratory.ui.mediastore.QueryMediaCollectionActivity

class MainActivity : MenuActivity() {
    override fun prepareMenu() {
        addMenuItem("Composable", ComposableActivity::class.java)
        addMenuItem("Fold", FoldActivity::class.java)
        addMenuItem("Corner Layout", CornerActivity::class.java)
        addMenuItem("Scroller", ScrollerActivity::class.java)
        addMenuItem("Coordinator", CoordinatorActivity::class.java)
        addMenuItem("Multi Launcher", MultiLauncherActivity::class.java)
        addMenuItem("Touch Event", TouchEventActivity::class.java)
        addMenuItem("Multitouch", MultitouchActivity::class.java)
        addMenuItem("Rotary Knob", RotaryKnobActivity::class.java)
        addMenuItem("Custom EditText", CustomEditTextActivity::class.java)
        addMenuItem("Tabbed", TabbedActivity::class.java)
        addMenuItem("View to File", CaptureViewActivity::class.java)
        addMenuItem("Telephony", TelephonyActivity::class.java)
        addMenuItem("Pick File", PickFileActivity::class.java)
        addMenuItem("SurfaceView", SurfaceViewTestActivity::class.java)
        addMenuItem("RecordProgressBar", RecordProgressBarActivity::class.java)
        addMenuItem("Media Collection", QueryMediaCollectionActivity::class.java)
        addMenuItem("android.graphics.Camera", AndroidGraphicsCameraActivity::class.java)
        addMenuItem("Vertical Layout", VerticalLayoutActivity::class.java)
        addMenuItem("Floating Tracker Ball", TrackerBallActivity::class.java)
        addMenuItem("Scrolling Conflicts", ScrollingConflictsActivity::class.java)
        addMenuItem("Second Finger Tracer", SecondFingerTracerActivity::class.java)
        addMenuItem("Call JNI", JniActivity::class.java)
        addMenuItem("Room", RoomActivity::class.java)
        addMenuItem("Rx", RxActivity::class.java)
    }
}