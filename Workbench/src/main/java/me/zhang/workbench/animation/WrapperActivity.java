package me.zhang.workbench.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

public class WrapperActivity extends AppCompatActivity {

    public static final int DURATION = 3000;
    @InjectView(R.id.wrapperButton)
    Button mWrapperButton;

    @InjectView(R.id.wrapperText)
    TextView mWrapperText;

    @InjectView(R.id.wrapperView)
    View mWrapperView;

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
