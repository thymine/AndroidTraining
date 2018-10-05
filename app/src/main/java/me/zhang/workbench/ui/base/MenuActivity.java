package me.zhang.workbench.ui.base;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 5/11/2015 14:20 下午.
 */
public abstract class MenuActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, HistoryAdapter.OnHistoryItemClickListener {

    private static final String TAG = MenuActivity.class.getSimpleName();
    public static final String KEY_HISTORIES = "key_histories";
    private static final String NAME_HISTORIES = "name_histories";
    public static final int TO_INDEX = 10;

    private ArrayAdapter<String> mAdapter;
    private final List<String> historyKeys = new ArrayList<>();

    private SortedMap<String, Intent> actions = new TreeMap<>();
    private HistoryAdapter adapter;
    private RecyclerView historyList;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = (String) parent.getItemAtPosition(position);
        onClickItem(key);
    }

    private void onClickItem(String key) {
        saveClickedKey(key);
        startActivity(actions.get(key));
    }

    private void saveClickedKey(String key) {
        historyKeys.remove(key);
        historyKeys.add(0, key);
        saveHistoryKeys();
    }

    void saveHistoryKeys() {
        String histories = new Gson().toJson(historyKeys.subList(0, getToIndex(historyKeys))); // 最多保存10个历史记录
        SharedPreferences.Editor editor = getSharedPreferences(NAME_HISTORIES, MODE_PRIVATE).edit();
        editor.putString(KEY_HISTORIES, histories);
        editor.apply();
    }

    void restoreHistoryKeys() {
        SharedPreferences prefs = getSharedPreferences(NAME_HISTORIES, MODE_PRIVATE);
        String restoredHistories = prefs.getString(KEY_HISTORIES, null);
        List<String> historyList = new Gson().fromJson(restoredHistories, new TypeToken<List<String>>() {
        }.getType());

        if (historyList != null) {
            historyKeys.clear();
            historyKeys.addAll(historyList.subList(0, getToIndex(historyList))); // 最多展示10个历史记录
        }
        notifyHistoriesChanged();
    }

    private int getToIndex(@NonNull List<String> sourceList) {
        return sourceList.size() > TO_INDEX ? TO_INDEX : sourceList.size();
    }

    private void notifyHistoriesChanged() {
        adapter.notifyDataSetChanged();
        historyList.setVisibility(shouldShowHistoryPan() ? View.VISIBLE : View.GONE);
    }

    private boolean shouldShowHistoryPan() {
        return historyKeys.size() > 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        historyList = findViewById(R.id.historyList);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        historyList.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(historyKeys, this);
        historyList.setAdapter(adapter);

        ListView list = findViewById(android.R.id.list);
        list.setEmptyView(findViewById(android.R.id.empty));
        list.setOnItemClickListener(this);
        list.setFastScrollEnabled(true);

//        setTitle(R.string.lab); // define title inside AndroidManifest.xml

        prepareMenu();

        //noinspection ToArrayCallWithZeroLengthArrayArgument
        String[] keys = actions.keySet().toArray(new String[actions.keySet().size()]);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys);
        list.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreHistoryKeys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> historyList.setVisibility(!hasFocus && shouldShowHistoryPan() ? View.VISIBLE : View.GONE));

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //noinspection ConstantConditions
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

    @Override
    public void onClick(@NotNull String historyKey) {
        onClickItem(historyKey);
    }
}
