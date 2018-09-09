package me.zhang.workbench.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/23/2016 9:18 PM.
 */
public class TranslateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        final Button moveButton = (Button) findViewById(R.id.tweenButton);
        final Animation moveAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        assert moveButton != null;
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveButton.startAnimation(moveAnimation);
            }
        });

        Button propertyButton = (Button) findViewById(R.id.propertyButton);
        final ObjectAnimator propertyAnimator = ObjectAnimator
                .ofFloat(propertyButton, "translationX", 0, 100).setDuration(100);
        assert propertyButton != null;
        propertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyAnimator.start();
            }
        });

        final Button lpButton = (Button) findViewById(R.id.lpButton);
        assert lpButton != null;
        lpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) lpButton.getLayoutParams();
                lp.rightMargin += 100;
                lpButton.requestLayout();
            }
        });

    }
}
