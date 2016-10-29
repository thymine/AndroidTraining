package me.zhang.workbench.design.coordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.zhang.workbench.R;

public class CoordinateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
    }

    public void multipleElementsClick(View view) {
        startActivity(new Intent(this, MultipleElements.class));
    }

    public void multipleChaoticElementsClick(View view) {
        startActivity(new Intent(this, MultipleChaoticElements.class));
    }

    public void curvedMotionClick(View view) {
        startActivity(new Intent(this, CurveMotionList.class));
    }

    public void sizeChangeClick(View view) {
        startActivity(new Intent(this, SizeChange.class));
    }
}
