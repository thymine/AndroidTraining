package me.zhang.workbench.style;

import android.app.Activity;
import android.os.Bundle;

import me.zhang.workbench.R;

public class PreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        setContentView(R.layout.activity_preview);
    }
}
