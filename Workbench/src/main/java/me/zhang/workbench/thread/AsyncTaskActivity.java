package me.zhang.workbench.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class AsyncTaskActivity extends AppCompatActivity {

    public static final int MS = 50; // 50ms

    @BindView(R.id.downloadProgress)
    ProgressBar mDownloadProgress;

    @BindView(R.id.downloadProgress1)
    ProgressBar mDownloadProgress1;

    @BindView(R.id.downloadProgress2)
    ProgressBar mDownloadProgress2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

    }

    public void execute(View view) {
        resetProgressBar();

        DownloadTask mDownloadTask = new DownloadTask();
        DownloadTask1 mDownloadTask1 = new DownloadTask1();
        DownloadTask2 mDownloadTask2 = new DownloadTask2();
        switch (view.getId()) {
            case R.id.executeSerial:
                mDownloadTask.execute();
                mDownloadTask1.execute();
                mDownloadTask2.execute();
                break;
            case R.id.executeParallel:
                mDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mDownloadTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mDownloadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }

    private void resetProgressBar() {
        mDownloadProgress.setProgress(0);
        mDownloadProgress1.setProgress(0);
        mDownloadProgress2.setProgress(0);
    }

    class DownloadTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(MS);

                if (isCancelled()) {
                    break;
                }
                publishProgress(i + 1);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(AsyncTaskActivity.this, "下载取消！", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AsyncTaskActivity.this, "下载完毕！", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDownloadProgress.setProgress(values[0]);
        }
    }

    class DownloadTask1 extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(MS);

                if (isCancelled()) {
                    break;
                }
                publishProgress(i + 1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDownloadProgress1.setProgress(values[0]);
        }
    }

    class DownloadTask2 extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                SystemClock.sleep(MS);

                if (isCancelled()) {
                    break;
                }
                publishProgress(i + 1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDownloadProgress2.setProgress(values[0]);
        }
    }

}
