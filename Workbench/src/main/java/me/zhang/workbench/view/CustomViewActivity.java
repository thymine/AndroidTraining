package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.workbench.R;

/**
 * Created by Zhang on 2016/7/4 上午 9:41 .
 */
public class CustomViewActivity extends AppCompatActivity {

    @BindView(R.id.button_click)
    Button mClick;

    @BindView(R.id.tally_counter)
    TallyCounterView mTallyCounter;

    @BindView(R.id.button_reset)
    Button mReset;

    private int mCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_click)
    public void click(View view) {
        mTallyCounter.setCount(++mCount);
    }

    @OnClick(R.id.button_reset)
    public void reset(View view) {
        mTallyCounter.reset();
    }

}
