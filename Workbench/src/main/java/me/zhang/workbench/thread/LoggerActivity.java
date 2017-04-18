package me.zhang.workbench.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import me.zhang.workbench.R;

import static me.zhang.workbench.thread.Logger.MAX_QUEUE_SIZE;

public class LoggerActivity extends AppCompatActivity {

    private Logger logger;
    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Logger.WHAT_LOG:
                    Bundle data = msg.getData();
                    String message = data.getString(Logger.MESSAGE_LOG);
                    String text = String.valueOf(logTextView.getText()) + "\n" + message;
                    logTextView.setText(text);
                    logScrollView.fullScroll(View.FOCUS_DOWN);
                    break;
            }
            return false;
        }
    };
    private Handler handler = new Handler(callback);
    private TextView logTextView;
    private ScrollView logScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);
        logScrollView = (ScrollView) findViewById(R.id.log_scrollview);
        logTextView = (TextView) findViewById(R.id.log_textview);

        logger = new Logger(handler);
        logger.start();
    }

    public void log(View view) {
        for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
            logger.pushMessage("Log Message #" + i);
        }
    }

}
