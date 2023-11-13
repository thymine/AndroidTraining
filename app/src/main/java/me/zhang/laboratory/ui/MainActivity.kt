package me.zhang.laboratory.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import me.zhang.laboratory.ui.base.MenuActivity
import me.zhang.laboratory.ui.compose.AnimateActivity
import me.zhang.laboratory.ui.compose.BilibiliActivity
import me.zhang.laboratory.ui.compose.ComposableActivity
import me.zhang.laboratory.ui.compose.ConstraintActivity
import me.zhang.laboratory.ui.compose.DrawActivity
import me.zhang.laboratory.ui.compose.LayoutActivity
import me.zhang.laboratory.ui.compose.ListActivity
import me.zhang.laboratory.ui.compose.LoginActivity
import me.zhang.laboratory.ui.compose.ModifierActivity
import me.zhang.laboratory.ui.compose.SSOTActivity
import me.zhang.laboratory.ui.compose.ScaffoldActivity
import me.zhang.laboratory.ui.compose.StateActivity
import me.zhang.laboratory.ui.compose.WebViewActivity
import me.zhang.laboratory.ui.coroutines.CoroutinesActivity
import me.zhang.laboratory.ui.firebase.TestFirebaseActivity
import me.zhang.laboratory.ui.mediastore.QueryMediaCollectionActivity
import me.zhang.laboratory.ui.navi.NavActivity


class MainActivity : MenuActivity() {

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // Inform user that that your app will not show notifications.
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        isGooglePlayServicesAvailable(this)
    }

    override fun onResume() {
        isGooglePlayServicesAvailable(this)
        super.onResume()
    }

    private fun isGooglePlayServicesAvailable(activity: Activity): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404)?.show()
                // googleApiAvailability.makeGooglePlayServicesAvailable(this)
            }
            return false
        }
        return true
    }

    override fun prepareMenu() {
        addMenuItem("Navigation", NavActivity::class.java)
        addMenuItem("Coroutines", CoroutinesActivity::class.java)
        addMenuItem("Compose-Animate", AnimateActivity::class.java)
        addMenuItem("Compose-Draw", DrawActivity::class.java)
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
        addMenuItem("Test Firebase", TestFirebaseActivity::class.java)
    }

}