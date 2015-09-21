package me.zhang.lab.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Zhang on 9/21/2015 10:06 下午.
 * <p/>
 * A basic Camera preview class
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CameraView";
    private SurfaceHolder holder;
    private Camera camera;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraView(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        /**
         * Install a SurfaceHolder.Callback
         * so we get notified when the underlying surface is created and destroyed
         */
        holder = getHolder();
        holder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, new tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (holder.getSurface() == null) {
            // preview surface dose not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(this.holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

        /**
         * If you want to set a specific size for your camera preview,
         * set this in the surfaceChanged() method as noted in the comments above.
         * When setting preview size, you must use values from getSupportedPreviewSizes().
         * Do not set arbitrary values in the setPreviewSize() method.
         */

        /**
         * Note: A camera preview does not have to be in landscape mode.
         * Starting in Android 2.2 (API Level 8),
         * you can use the setDisplayOrientation() method to set the rotation of the preview image.
         * In order to change preview orientation as the user re-orients the phone,
         * within the surfaceChanged() method of your preview class,
         * first stop the preview with Camera.stopPreview() change the orientation
         * and then start the preview again with Camera.startPreview().
         */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }
}
