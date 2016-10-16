package me.zhang.workbench.design;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

public class DynamicSurfacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_surfaces);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

}
