package me.zhang.workbench.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.zhang.workbench.R;

public class BusActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Subscribe
    public void onMobileNetConnectedEvent(MobileNetConnectedEvent event) {
        mTextView.setText(event.detailedState);
    }

    @Subscribe
    public void onMobileNetDisconnectedEvent(MobileNetDisconnectedEvent event) {
        mTextView.setText("Net Disconnected");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
