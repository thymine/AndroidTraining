package me.zhang.lab.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.zhang.lab.R;
import me.zhang.lab.utils.HardwareUtils;

/**
 * Created by Zhang on 9/21/2015 10:23 下午.
 */
public class CameraActivity extends Activity {

    private static final String TAG = "CameraActivity";

    private static final int MEDIA_TYPE_IMAGE = 0;
    private Camera camera;

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            savePicture(data);
        }

    };

    private void savePicture(byte[] data) {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions.");
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(pictureFile);
            out.write(data);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            playShutterSound();
        }
    };

    private void playShutterSound() {
        SoundPool pool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        int shutterSound = pool.load(CameraActivity.this, R.raw.camera_click, 0);
        pool.play(shutterSound, 1f, 1f, 0, 0, 1);
    }

    private File getOutputMediaFile(int mediaTypeImage) {
        switch (mediaTypeImage) {
            case MEDIA_TYPE_IMAGE:
                String fileDir = HardwareUtils.getOutputFileDir(this).getAbsolutePath()
                        + File.separator
                        + Long.toString(System.currentTimeMillis())
                        + ".jpg";
                return new File(fileDir);
        }
        return null;
    }

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

        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get an image from the camera
                camera.takePicture(shutterCallback, null, pictureCallback);
            }
        });
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
