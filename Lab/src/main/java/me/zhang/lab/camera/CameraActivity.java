package me.zhang.lab.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

import me.zhang.lab.R;
import me.zhang.lab.utils.HardwareUtils;

/**
 * Created by Zhang on 9/21/2015 10:23 下午.
 */
public class CameraActivity extends Activity {

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        camera = HardwareUtils.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        CameraView preview = new CameraView(this, camera);
        FrameLayout container = (FrameLayout) findViewById(R.id.camera_preview);
        container.addView(preview);
    }

    @Override
    protected void onStop() {
        releaseCamera();
        super.onStop();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }
}
