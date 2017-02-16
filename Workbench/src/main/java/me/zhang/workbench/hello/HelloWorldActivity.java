package me.zhang.workbench.hello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HelloWorldActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButton;
    int mTouchCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mButton = new Button(this);
        mButton.setText("Touch me!");
        mButton.setOnClickListener(this);
        setContentView(mButton);
    }

    @Override
    public void onClick(View v) {
        mTouchCount++;
        mButton.setText("Touch me " + mTouchCount + " time(s)");
    }
}
