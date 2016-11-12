package me.zhang.workbench.animation;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

public class WrapperActivity extends AppCompatActivity {

    public static final int DURATION = 3000;
    private static final String TAG = WrapperActivity.class.getSimpleName();
    @InjectView(R.id.wrapperButton)
    Button mWrapperButton;

    @InjectView(R.id.wrapperText)
    TextView mWrapperText;

    @InjectView(R.id.wrapperView)
    View mWrapperView;

    @InjectView(R.id.valueAnimatorView)
    View mValueAnimatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper);
        ButterKnife.inject(this);

        mWrapperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAnimation(mWrapperButton);
            }
        });

        mWrapperText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAnimation(mWrapperText);
            }
        });

        mWrapperView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
//                performAnimation(mWrapperView); // not working
                ViewWrapper wrapper = new ViewWrapper(mWrapperView);
                ObjectAnimator.ofInt(wrapper, "width", mWrapperView.getWidth() * 2)
                        .setDuration(DURATION).start();
            }
        });

        mValueAnimatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int start = mValueAnimatorView.getWidth();
                final int end = mValueAnimatorView.getWidth() * 2;
                ValueAnimator animator = ValueAnimator.ofInt(1, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    IntEvaluator mEvaluator = new IntEvaluator();

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        Log.i(TAG, "onAnimationUpdate: " + currentValue);

                        float fraction = animation.getAnimatedFraction();
                        mValueAnimatorView.getLayoutParams().width = mEvaluator.evaluate(fraction,
                                start, end);
                        mValueAnimatorView.requestLayout();
                    }
                });
                animator.setDuration(DURATION).start();
            }
        });
    }

    private void performAnimation(View target) {
        ObjectAnimator.ofInt(target, "width", target.getWidth() * 2)
                .setDuration(DURATION).start();
    }

    private static class ViewWrapper {
        private View mTargetView;

        public ViewWrapper(View targetView) {
            this.mTargetView = targetView;
        }

        public int getWidth() {
            return mTargetView.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTargetView.getLayoutParams().width = width;
            mTargetView.requestLayout();
        }

    }

}
