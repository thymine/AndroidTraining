package com.soufun.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import de.greenrobot.event.EventBus;

/**
 * Created by Zhang on 2015/7/23 上午 9:20 .
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent event = new MessageEvent("Message From Second Activity");
                EventBus.getDefault().post(event);
            }
        });
    }
}
