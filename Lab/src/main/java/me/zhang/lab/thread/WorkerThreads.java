package me.zhang.lab.thread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2016/2/2 下午 2:16 .
 */
public class WorkerThreads extends Activity {

    private static final String TAG = WorkerThreads.class.getSimpleName();
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(...)");

        setContentView(R.layout.activity_worker_threads);

        mText = (TextView) findViewById(R.id.text);
    }

    public void startWorker(View view) {
        new UpdateTextTask().execute();
    }

    private class UpdateTextTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int tick = 0;
            while (tick <= 10) {
                tick++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(10 * tick);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mText.setText(String.format("%s%%", values[0]));
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            mText.setText("Done.");

            Button button = (Button) findViewById(R.id.button);
            Log.i(TAG, "button: " + button);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
