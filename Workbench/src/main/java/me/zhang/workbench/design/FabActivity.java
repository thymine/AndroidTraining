package me.zhang.workbench.design;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.zhang.workbench.R;

public class FabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
    }

    public void fab(View view) {
        Snackbar.make(findViewById(R.id.activity_fab), "Floating", Snackbar.LENGTH_LONG).show();
    }
}
