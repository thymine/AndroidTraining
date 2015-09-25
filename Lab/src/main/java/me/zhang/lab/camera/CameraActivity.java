package me.zhang.lab.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
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

    private Camera camera;
    private boolean isCaptured = false;
    private CameraView preview;

    private MediaRecorder recorder;
    private boolean isRecording = false;

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            savePicture(data);
        }

    };

    private void savePicture(byte[] data) {
        File pictureFile = HardwareUtils.getOutputMediaFile(HardwareUtils.MEDIA_TYPE_IMAGE);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        camera = HardwareUtils.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        preview = new CameraView(this, camera);
        FrameLayout container = (FrameLayout) findViewById(R.id.camera_preview);
        container.addView(preview);

        // Add a listener to the Capture button
        final Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCaptured) {
                    // restart preview
                    camera.stopPreview();
                    camera.startPreview();

                    // inform user that you can take picture now
                    captureButton.setText("Capture");
                    isCaptured = false;
                } else {
                    // get an image from the camera
                    camera.takePicture(shutterCallback, null, pictureCallback);

                    // inform user that a picture has been captured
//                    captureButton.setText("Captured"); // TODO: 2015/9/25 修复 startPreview failed
                    isCaptured = true;
                }
            }

        });

        // Add a listener to the Record button
        final Button recordButton = (Button) findViewById(R.id.button_record);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    // stop recording and release camera
                    recorder.stop(); // stop the recording
                    releaseMediaRecorder(); // release the MediaRecorder object
                    camera.lock(); // take camera access back from MediaRecorder

                    // inform the user that recording has stopped
                    recordButton.setText("Record");
                    isRecording = false;
                } else {
                    // initialize video camera
                    if (prepareVideoRecorder()) {
                        // Camera is available and unlocked, MediaRecorder is prepared,
                        // now you can start recording
                        recorder.start();

                        // inform the uesr that recording has started
                        recordButton.setText("Stop");
                        isRecording = true;
                    } else {
                        // prepare didn't work, release the camera
                        releaseMediaRecorder();
                        // inform user
                        recordButton.setText("Not Available");
                    }
                }
            }

        });
    }

    private boolean prepareVideoRecorder() {
        recorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        recorder.setCamera(camera);

        // Step 2: Set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

//        // Step 3: Set output format and encoding (for versions prior to API Level 8)
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        // Step 4: Set output file
        recorder.setOutputFile(HardwareUtils.getOutputMediaFile(HardwareUtils.MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        recorder.setPreviewDisplay(preview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder(); // if you are using MediaRecorder, release it first
        releaseCamera(); // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (recorder != null) {
            recorder.reset(); // clear recorder configuration
            recorder.release(); // release the recorder object
            recorder = null;
            camera.lock(); // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release(); // release the camera for other applications
            camera = null;
        }

        /**
         * Caution: If your application does not properly release the camera,
         * all subsequent attempts to access the camera,
         * including those by your own application,
         * will fail and may cause your or other applications to be shut down.
         */
    }
}
