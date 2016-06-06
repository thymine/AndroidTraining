package me.zhang.art.activity.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.zhang.art.R;

/**
 * Created by Li on 6/5/2016 2:24 PM.
 */
public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("CodeDialogActivity"); // http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android

        setContentView(R.layout.activity_dialog);
    }
}
