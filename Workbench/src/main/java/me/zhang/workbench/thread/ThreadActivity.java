package me.zhang.workbench.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.zhang.workbench.R;

public class ThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.threadLocal:
                startActivity(new Intent(this, ThreadLocalActivity.class));
                break;
            case R.id.asyncTask:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
        }
    }

}
