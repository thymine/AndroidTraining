package me.zhang.workbench.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhang on 8/18/2016 2:56 PM.
 */
public class WorkerThread extends HandlerThread {

    private Handler mWorkerHandler;
    private Handler mResponseHandler;
    private static final String TAG = WorkerThread.class.getSimpleName();
    private Map<ImageView, String> mRequestMap = new HashMap<>();
    private Callback mCallback;

    public interface Callback {
        void onImageDownloaded(ImageView imageView, Bitmap bitmap, int side);
    }

    public WorkerThread(Handler responseHandler, Callback callback) {
        super(TAG);
        mResponseHandler = responseHandler;
        mCallback = callback;
    }

    public void queueTask(String url, int side, ImageView imageView) {
        mRequestMap.put(imageView, url);
        Log.i(TAG, url + " added to the queue");
        mWorkerHandler.obtainMessage(side, imageView).sendToTarget();
    }

    public void prepareHandler() {
        mWorkerHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ImageView imageView = (ImageView) msg.obj;
                String side = msg.what == MainActivity.LEFT_SIDE ? "left side" : "right side";
                Log.i(TAG, String.format("Processing %s, %s", mRequestMap.get(imageView), side));
                handleRequest(imageView, msg.what);
                return true;
            }
        });
    }

    private void handleRequest(final ImageView imageView, final int side) {
        String url = mRequestMap.get(imageView);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) connection.getContent());
            mRequestMap.remove(imageView);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() { // running on UI thread
                    mCallback.onImageDownloaded(imageView, bitmap, side);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
