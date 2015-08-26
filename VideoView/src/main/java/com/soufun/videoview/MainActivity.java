package com.soufun.videoview;

import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean isRecording = false;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recorder = new MediaRecorder();
        initRecorder();
        initPlayer();

        setContentView(R.layout.activity_main);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.CameraView);
        holder = cameraView.getHolder();
        holder.addCallback(this);

        cameraView.setClickable(true);
    }

    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        recorder.setOutputFile("/sdcard/videocapture_example.mp4");
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private void initPlayer() {
        mp = new MediaPlayer();
        try {
            mp.setDataSource("/sdcard/videocapture_example.mp4");
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setScreenOnWhilePlaying(true);
        mp.setDisplay(holder);
    }

    /**
     * 录制视频
     */
    public void record(View view) {
        if (isRecording) {
            recorder.stop();
            isRecording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            isRecording = true;
            recorder.start();
        }
    }

    /**
     * 播放视频
     */
    public void play(View view) {
        if (mp.isPlaying()) {
            mp.pause();
            mp.stop();
        } else {
            mp.start();
        }
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (isRecording) {
            recorder.stop();
            isRecording = false;
        }
        recorder.release();
        finish();
    }

}
