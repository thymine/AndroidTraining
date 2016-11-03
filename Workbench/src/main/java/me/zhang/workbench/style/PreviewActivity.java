package me.zhang.workbench.style;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        setContentView(R.layout.activity_preview);
    }
}
