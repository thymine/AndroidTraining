package me.zhang.commonadapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<Bean> mDatas;
    private ListView mListView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();

        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.id_listview);
        mListView.setAdapter(mAdapter);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();

        for (int i = 100; i < 200; i++) {
            Bean bean = new Bean(
                    "Android",
                    "Android Android Android Android Android",
                    "2015/6/9",
                    "10" + i
            );
            mDatas.add(bean);
        }

//        mAdapter = new OldAdapter(this, mDatas);
        mAdapter = new NewAdapter(this, mDatas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
