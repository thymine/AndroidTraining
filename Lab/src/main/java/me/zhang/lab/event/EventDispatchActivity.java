package me.zhang.lab.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import me.zhang.lab.R;

public class EventDispatchActivity extends AppCompatActivity {

    private static final String TAG = EventDispatchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "### button onTouch : " + event.getAction());
//                return false;
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "### button onClick : " + v);
            }
        });

        Log.d(TAG, "### activity getWindow() : " + this.getWindow());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}
