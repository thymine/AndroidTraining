package me.zhang.lab.listview;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zhang on 8/6/2015 9:18 下午.
 */
public class ListViewActivity extends Activity {

    private List<Item> items;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(me.zhang.lab.R.layout.activity_listview);

        ListView listView = (ListView) findViewById(me.zhang.lab.R.id.lv_list);
        listView.setItemsCanFocus(true);

        items = new ArrayList<>();
        adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = items.get(position);
                item.title = String.valueOf(Integer.parseInt(item.title) + 1);
                adapter.notifyDataSetChanged();
            }

        });

        new ProduceDataTask().execute();
    }

    class ProduceDataTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... params) {
            Random random = new Random(System.currentTimeMillis());
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Item item = new Item();
                item.title = String.valueOf(random.nextInt(100));
                items.add(item);
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Item> result) {
            super.onPostExecute(result);

            if (result != null && result.size() > 0) {
                items.clear();
                items.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
