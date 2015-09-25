The general steps for creating a custom camera interface for your application are as follows:

    1. Detect and Access Camera - Create code to check for the existence of cameras and request access.
    2. Create a Preview Class - Create a camera preview class that extends SurfaceView and implements the SurfaceHolder interface. This class previews the live images from the camera.
    3. Build a Preview Layout - Once you have the camera preview class, create a view layout that incorporates the preview and the user interface controls you want.
    4. Setup Listeners for Capture - Connect listeners for your interface controls to start image or video capture in response to user actions, such as pressing a button.
    5. Capture and Save Files - Setup the code for capturing pictures or videos and saving the output.
    6. Release the Camera - After using the camera, your application must properly release it for use by other applications.

Caution: Remember to release the Camera object by calling the Camera.release() when your application is done using it! If your application does not properly release the camera, all subsequent attempts to access the camera, including those by your own application, will fail and may cause your or other applications to be shut down.

Unlike taking pictures with a device camera, capturing video requires a very particular call order. You must follow a specific order of execution to successfully prepare for and capture video with your application, as detailed below.

    1. Open Camera - Use the Camera.open() to get an instance of the camera object.
    2. Connect Preview - Prepare a live camera image preview by connecting a SurfaceView to the camera using Camera.setPreviewDisplay().
    3. Start Preview - Call Camera.startPreview() to begin displaying the live camera images.
    4. Start Recording Video - The following steps must be completed in order to successfully record video:
        a. Unlock the Camera - Unlock the camera for use by MediaRecorder by calling Camera.unlock().
        b. Configure MediaRecorder - Call in the following MediaRecorder methods in this order.
            ①. setCamera() - Set the camera to be used for video capture, use your application's current instance of Camera.
            ②. setAudioSource() - Set the audio source, use MediaRecorder.AudioSource.CAMCORDER.
            ③. setVideoSource() - Set the video source, use MediaRecorder.VideoSource.CAMERA.
            ④. Set the video output format and encoding. For Android 2.2 (API Level 8) and higher, use the MediaRecorder.setProfile method, and get a profile instance using CamcorderProfile.get(). For versions of Android prior to 2.2, you must set the video output format and encoding parameters:

                   setOutputFormat() - Set the output format, specify the default setting or MediaRecorder.OutputFormat.MPEG_4.
                   setAudioEncoder() - Set the sound encoding type, specify the default setting or MediaRecorder.AudioEncoder.AMR_NB.
                   setVideoEncoder() - Set the video encoding type, specify the default setting or MediaRecorder.VideoEncoder.MPEG_4_SP.

            ⑤. setOutputFile() - Set the output file.
            ⑥. setPreviewDisplay() - Specify the SurfaceView preview layout element for your application. Use the same object you specified for Connect Preview.
            Caution: You must call these MediaRecorder configuration methods in this order, otherwise your application will encounter errors and the recording will fail.
        c. Prepare MediaRecorder - Prepare the MediaRecorder with provided configuration settings by calling MediaRecorder.prepare().
        d. Start MediaRecorder - Start recording video by calling MediaRecorder.start().
    5. Stop Recording Video - Call the following methods in order, to successfully complete a video recording:
        a. Stop MediaRecorder - Stop recording video by calling MediaRecorder.stop().
        b. Reset MediaRecorder - Optionally, remove the configuration settings from the recorder by calling MediaRecorder.reset().
        c. Release MediaRecorder - Release the MediaRecorder by calling MediaRecorder.release().
        d. Lock the Camera - Lock the camera so that future MediaRecorder sessions can use it by calling Camera.lock(). Starting with Android 4.0 (API level 14), this call is not required unless the MediaRecorder.prepare() call fails.
    6. Stop the Preview - When your activity has finished using the camera, stop the preview using Camera.stopPreview().
    7. Release Camera - Release the camera so that other applications can use it by calling Camera.release().
        Note: It is possible to use MediaRecorder without creating a camera preview first and skip the first few steps of this process. However, since users typically prefer to see a preview before starting a recording, that process is not discussed here.
        Tip: If your application is typically used for recording video, set setRecordingHint(boolean) to true prior to starting your preview. This setting can help reduce the time it takes to start recording.

