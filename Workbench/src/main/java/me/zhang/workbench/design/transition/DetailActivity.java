package me.zhang.workbench.design.transition;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar_Details);
        setContentView(R.layout.activity_detail);

        final int clickedColor = getIntent().getIntExtra(getString(R.string.key_clicked_color), Color.WHITE);
        findViewById(R.id.v_clicked_color).setBackgroundColor(clickedColor);
    }
}
