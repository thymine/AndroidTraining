package me.zhang.workbench.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class AsyncTaskActivity extends AppCompatActivity {

    public static final int MS = 200;

    @BindView(R.id.downloadProgress)
    ProgressBar mDownloadProgress;

    private DownloadTask mDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

        mDownloadTask = new DownloadTask();
        mDownloadTask.execute();
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

    @Override
    protected void onDestroy() {
        mDownloadTask.cancel(true);
        super.onDestroy();
    }
}
