package me.zhang.workbench.resources;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.BindView;
import me.zhang.workbench.R;

public class TestGetDimens extends AppCompatActivity {

    @BindView(R.id.dp)
    TextView mDp;

    @BindView(R.id.px)
    TextView mPx;

    @BindView(R.id.idp)
    TextView mIdp;

    @BindView(R.id.ipx)
    TextView mIpx;

    @BindView(R.id.sp)
    TextView mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_get_dimens);
        ButterKnife.bind(this);

        mDp.setText(getString(R.string.dp, getResources().getDimension(R.dimen.dp)));
        mPx.setText(getString(R.string.px, getResources().getDimension(R.dimen.px)));

        mIdp.setText(getString(R.string.idp, getResources().getDimensionPixelSize(R.dimen.dp)));
        mIpx.setText(getString(R.string.ipx, getResources().getDimensionPixelSize(R.dimen.px)));

        mSp.setText(getString(R.string.sp, getResources().getDimensionPixelSize(R.dimen.sp)));
    }
}
