package me.zhang.workbench.state;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

public class RestoreActivityState extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_state);
    }
}
