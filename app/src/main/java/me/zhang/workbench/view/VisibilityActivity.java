package me.zhang.workbench.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import me.zhang.workbench.R;

/**
 * Created by Li on 2/22/2016 9:34 PM.
 */
public class VisibilityActivity extends Activity {

    private Button hiddenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visibility);

        hiddenButton = (Button) findViewById(R.id.btn_hide);
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VisibilityActivity.this, "I'm hidden button, hi~", Toast.LENGTH_SHORT).show();
            }
        });

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.btn_toggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    hiddenButton.setVisibility(View.VISIBLE);
                else
                    hiddenButton.setVisibility(View.GONE);
            }
        });
    }

}
