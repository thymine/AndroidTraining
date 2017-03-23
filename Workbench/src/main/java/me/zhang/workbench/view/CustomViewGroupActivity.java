package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static me.zhang.workbench.view.DataSource.buildCustomItmes;

public class CustomViewGroupActivity extends AppCompatActivity {

    @BindView(R.id.custom_viewgroup_list)
    RecyclerView mCustomViewGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_group);
        ButterKnife.bind(this);

        mCustomViewGroupList.setHasFixedSize(true);
        mCustomViewGroupList.setLayoutManager(new LinearLayoutManager(this));
        mCustomViewGroupList.setAdapter(new CustomViewGroupAdapter(buildCustomItmes(50)));
    }

}
