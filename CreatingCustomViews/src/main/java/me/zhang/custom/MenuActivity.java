package me.zhang.custom;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Zhang on 4/26/2015 10:52 上午.
 */
public abstract class MenuActivity extends ListActivity {

    private SortedMap<String, Intent> actions = new TreeMap<>();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String key = (String) l.getItemAtPosition(position);
        startActivity(actions.get(key));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareMenu();

        String[] keys = actions.keySet().toArray(
                new String[actions.keySet().size()]
        );

        setListAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys)
        );

    }

    protected void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }

    protected abstract void prepareMenu();
}
