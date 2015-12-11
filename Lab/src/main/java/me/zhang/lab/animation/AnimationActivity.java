package me.zhang.lab.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.zhang.lab.R;

public class AnimationActivity extends AppCompatActivity {

    private static final String TAG = AnimationActivity.class.getSimpleName();

    private TextView tv_object_animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        objectAnimator();

        wrapperView();

        propertyValuesHolder(findViewById(R.id.tv_property_values_holder));

        valueAnimator();
    }

    private void valueAnimator() {
        final ImageView iv_value_animator = (ImageView) findViewById(R.id.iv_value_animator);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setTarget(iv_value_animator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                iv_value_animator.setTranslationY(value);
            }
        });
        animator.setDuration(1000).start();
    }

    private void propertyValuesHolder(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY)
                .setDuration(1000).start();
    }

    private void wrapperView() {
        Button btn_view_wrapper = (Button) findViewById(R.id.btn_view_wrapper);
        WrapperView wrapperView = new WrapperView(btn_view_wrapper);
        ObjectAnimator.ofInt(wrapperView, "width", 300, 900).setDuration(3000).start();
    }

    private void objectAnimator() {
        tv_object_animator = (TextView) findViewById(R.id.tv_object_animator);

        tv_object_animator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getObjectAnimator().start();
            }
        });
    }

    @NonNull
    private ObjectAnimator getObjectAnimator() {
        final ObjectAnimator anim = ObjectAnimator
                .ofFloat(tv_object_animator, "rotationX", 0.0f, 360.0f)
                .setDuration(2000);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.i(TAG, "value: " + value);

                float ratio = value / 360;

                tv_object_animator.setAlpha(ratio);

                tv_object_animator.setScaleX(ratio);
            }
        });
        return anim;
    }

    private static class WrapperView {
        private final View view;

        public WrapperView(View view) {
            this.view = view;
        }

        public int getWidth() {
            return view.getLayoutParams().width;
        }

        public void setWidth(int width) {
            view.getLayoutParams().width = width;
            view.requestLayout();
        }
    }

}
