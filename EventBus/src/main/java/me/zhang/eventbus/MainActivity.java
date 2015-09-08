package me.zhang.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {

    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Zhang", "MainActivity.onCreate(...)");
        EventBus.getDefault().register(this);

        message = (TextView) findViewById(R.id.tv_message);

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(MessageEvent event) {
        String msg = "onEvent: " + event.getMessage();
        message.setText(msg);

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        Log.i("Zhang", "MainActivity.onDestroy()");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
