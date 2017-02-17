package me.zhang.workbench.base;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.SortedMap;
import java.util.TreeMap;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 5/11/2015 14:20 下午.
 */
public abstract class MenuActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    private static final String TAG = MenuActivity.class.getSimpleName();
    private ArrayAdapter<String> mAdapter;

    private SortedMap<String, Intent> actions = new TreeMap<>();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = (String) parent.getItemAtPosition(position);
        startActivity(actions.get(key));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setEmptyView(findViewById(android.R.id.empty));
        list.setOnItemClickListener(this);
        list.setFastScrollEnabled(true);

//        setTitle(R.string.lab); // define title inside AndroidManifest.xml

        prepareMenu();

        String[] keys = actions.keySet().toArray(new String[actions.keySet().size()]);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys);
        list.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);

        return true;
    }

    protected void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }

    protected abstract void prepareMenu();

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange: " + newText);
        mAdapter.getFilter().filter(newText);
        return true;
    }

}
