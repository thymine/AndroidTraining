package me.zhang.lab;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang on 8/6/2015 9:18 下午.
 */
public class ListViewActivity extends Activity {

    private List<Item> items;
    private LVAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(me.zhang.lab.R.layout.activity_listview);

        MyListView listView = (MyListView) findViewById(me.zhang.lab.R.id.lv_list);
        items = new ArrayList<>();
        lvAdapter = new LVAdapter(this, items);
        listView.setAdapter(lvAdapter);
        new ListTask().execute();
    }

    class ListTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... params) {
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Item item = new Item();
                item.title = Integer.toString(i + 1);
                items.add(item);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Item> result) {
            super.onPostExecute(result);

            if (result != null && result.size() > 0) {
                items.clear();
                items.addAll(result);
                lvAdapter.notifyDataSetChanged();
            }
        }
    }

}
