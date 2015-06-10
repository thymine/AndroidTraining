package me.zhang.commonadapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.zhang.commonadapter.utils.CommonAdapter;
import me.zhang.commonadapter.utils.ViewHolder;

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
//        mListView.setAdapter(mAdapter);
        /* Using anonymous inner class directly */
        mListView.setAdapter(
                new CommonAdapter<Bean>(MainActivity.this, mDatas, R.layout.item_listview) {

                    private List<Integer> mCheckedPostion = new ArrayList<>();

                    @Override
                    public void fillDatas(final ViewHolder holder, final Bean bean) {
                        holder
                                .setText(R.id.id_title, bean.getTitle())
                                .setText(R.id.id_desc, bean.getDesc())
                                .setText(R.id.id_time, bean.getTime())
                                .setText(R.id.id_phone, bean.getPhone());

                        final CheckBox check = holder.getView(R.id.id_check);
                        ////////////////// Method - 1 /////////////////////
                        /* Load checked status from bean *//*
                        check.setChecked(bean.isChecked());
                        check.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bean.setChecked(check.isChecked());
                                    }
                                }
                        );*/

                        ////////////////// Method - 2 /////////////////////
                        check.setChecked(false);
                        /* Check whether current position is checked - was it added? */
                        if (mCheckedPostion.contains(holder.getPosition())) {
                            check.setChecked(true);
                        }
                        check.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (check.isChecked()) {
                                            /* Add checked postion to ArrayList */
                                            mCheckedPostion.add(holder.getPosition());
                                        } else {
                                            mCheckedPostion.remove((Integer) holder.getPosition());
                                        }
                                    }
                                }
                        );
                    }
                }
        );
    }

    private void initDatas() {
        mDatas = new ArrayList<>();

        for (int i = 100; i < 200; i++) {
            Bean bean = new Bean(
                    "Android",
                    "Description Description Description Description Description",
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
