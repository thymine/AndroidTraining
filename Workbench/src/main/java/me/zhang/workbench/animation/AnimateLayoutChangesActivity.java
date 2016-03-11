package me.zhang.workbench.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 2016/3/11 下午 1:59 .
 */
public class AnimateLayoutChangesActivity extends Activity {

    LinearLayout linearLayout;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animate_layout_changes);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
    }

    public void add(View view) {
        final Button button = new Button(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setText(String.format(getResources().getString(R.string.btn_remove), ++count));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(button);
            }
        });
        linearLayout.addView(button, 2);
    }
}
