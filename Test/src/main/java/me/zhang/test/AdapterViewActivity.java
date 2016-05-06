package me.zhang.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhang on 2016/5/6 下午 4:42 .
 */
public class AdapterViewActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    Random random = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view);

        ButterKnife.bind(this);

        List<Data> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(new Data(String.format("%s", random.nextInt(10000))));
        }

        listView.setAdapter(new MyAdapter(this, datas));
    }

}
