package me.zhang.workbench.lifecycle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class FragmentLifecycleInsideViewPager extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.tab)
    TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lifecycle_inside_view_pager);
        ButterKnife.bind(this);

        mPager.setAdapter(new LifecyclePagerAdapter(getSupportFragmentManager()));
        mTab.setupWithViewPager(mPager);
    }
}
