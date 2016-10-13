package me.zhang.workbench.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.zhang.workbench.R;
import me.zhang.workbench.adapter.contract.Heardable;
import me.zhang.workbench.adapter.model.Car;
import me.zhang.workbench.adapter.model.Dog;
import me.zhang.workbench.adapter.model.Duck;
import me.zhang.workbench.adapter.model.Rat;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class AdapterActivity extends AppCompatActivity {

    private List<Heardable> mModels;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

        mModels = new ArrayList<>();
        mAdapter = new Adapter(this, mModels);

        recyclerView.setAdapter(mAdapter);

        fetchDatas();
    }

    /**
     * 生成假数据
     */
    private void fetchDatas() {
        for (int i = 0; i < 5; i++) {
            mModels.add(new Duck());
        }

        for (int i = 0; i < 5; i++) {
            mModels.add(new Car());
        }

        for (int i = 0; i < 5; i++) {
            mModels.add(new Rat());
        }

        for (int i = 0; i < 5; i++) {
            mModels.add(new Dog());
        }

        Collections.shuffle(mModels);

        mAdapter.notifyDataSetChanged();
    }

}
