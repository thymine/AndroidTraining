package me.zhang.laboratory.ui

import me.zhang.laboratory.ui.base.MenuActivity
import me.zhang.laboratory.ui.compose.BilibiliActivity
import me.zhang.laboratory.ui.compose.ComposableActivity
import me.zhang.laboratory.ui.compose.ConstraintActivity
import me.zhang.laboratory.ui.compose.LayoutActivity
import me.zhang.laboratory.ui.compose.ListActivity
import me.zhang.laboratory.ui.compose.LoginActivity
import me.zhang.laboratory.ui.compose.ModifierActivity
import me.zhang.laboratory.ui.compose.SSOTActivity
import me.zhang.laboratory.ui.compose.ScaffoldActivity
import me.zhang.laboratory.ui.compose.StateActivity
import me.zhang.laboratory.ui.compose.WebViewActivity
import me.zhang.laboratory.ui.mediastore.QueryMediaCollectionActivity

class MainActivity : MenuActivity() {
    override fun prepareMenu() {
        addMenuItem("Compose-State", StateActivity::class.java)
        addMenuItem("Compose-List", ListActivity::class.java)
        addMenuItem("Compose-Scaffold", ScaffoldActivity::class.java)
        addMenuItem("Compose-Constraint", ConstraintActivity::class.java)
        addMenuItem("Compose-Layout", LayoutActivity::class.java)
        addMenuItem("Compose-Bilibili", BilibiliActivity::class.java)
        addMenuItem("Compose-Login", LoginActivity::class.java)
        addMenuItem("Compose-Modifier", ModifierActivity::class.java)
        addMenuItem("Compose-WebView", WebViewActivity::class.java)
        addMenuItem("Compose-SSOT", SSOTActivity::class.java)
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