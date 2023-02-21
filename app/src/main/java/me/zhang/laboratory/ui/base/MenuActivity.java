package me.zhang.laboratory.ui.base;

import android.animation.ValueAnimator;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import me.zhang.laboratory.R;
import me.zhang.laboratory.ui.BaseActivity;
import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

/**
 * Created by Zhang on 5/11/2015 14:20 下午.
 */
public abstract class MenuActivity extends BaseActivity
        implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, HistoryAdapter.OnHistoryItemClickListener {

    private static final String TAG = MenuActivity.class.getSimpleName();
    public static final String KEY_HISTORIES = "key_histories";
    private static final String NAME_HISTORIES = "name_histories";
    public static final int TO_INDEX = 10;

    private ArrayAdapter<String> mAdapter;
    private final List<String> historyKeys = new ArrayList<>();

    private SortedMap<String, Intent> actions = new TreeMap<>();
    private HistoryAdapter adapter;
    private ImageView recycleBin;
    private RecyclerView historyList;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = (String) parent.getItemAtPosition(position);
        clickItem(key);
    }

    private void clickItem(String key) {
        saveKey(key);
        Intent intent = actions.get(key);
        if (intent != null) {
            startActivity(intent);
        } else {
            removeKey(key);
            notifyHistoriesChanged();
            Toast.makeText(this, "Invalid entry.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveKey(String key) {
        historyKeys.remove(key);
        historyKeys.add(0, key);
        saveHistoryKeys();
    }

    private void removeKey(String key) {
        historyKeys.remove(key);
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

        recycleBin = findViewById(R.id.recycleBin);
        setDragListener();

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

    private void setDragListener() {
        recycleBin.setOnDragListener((v, event) -> {
            final int action = event.getAction();
            switch (action) {
                case DragEvent
                        .ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                    ((ImageView) v).setColorFilter(Color.RED);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    ((ImageView) v).clearColorFilter();
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    CharSequence historyItem = item.getText();
                    removeKey(historyItem.toString());
                    notifyHistoriesChanged();
                    Toast.makeText(this, "History item '" + historyItem + "' removed.", Toast.LENGTH_SHORT).show();

                    ((ImageView) v).clearColorFilter();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    ((ImageView) v).clearColorFilter();
                    Log.d(TAG, "Drop result: " + event.getResult());

                    collapseRecycleBin();
                    ((View) event.getLocalState()).setAlpha(1.0f);
                    return true;
                default:
                    Log.e(TAG, "Unknown action type received by OnDragListener.");
                    break;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreHistoryKeys();
        notifyHistoriesChanged();
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
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "主题切换…", Toast.LENGTH_SHORT).show();

                String skinName = SkinPreference.getInstance().getSkinName();
                String targetSkinName = "blue";
                if (targetSkinName.equals(skinName)) {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    Toast.makeText(this, "加载默认主题", Toast.LENGTH_SHORT).show();
                } else {
                    SkinCompatManager.getInstance().loadSkin(targetSkinName, null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                    Toast.makeText(this, "加载" + targetSkinName + "主题", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        clickItem(historyKey);
    }

    @Override
    public void onLongClick(@NotNull View v, float touchX, float touchY) {
        CharSequence tag = (CharSequence) v.getTag();
        ClipData.Item item = new ClipData.Item(tag);
        ClipData dragData = new ClipData(tag, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
        v.startDrag(dragData, new View.DragShadowBuilder(v) {
            @Override
            public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
                super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
                outShadowTouchPoint.set((int) touchX, (int) touchY);
            }
        }, v, 0);

        expandRecycleBin();
        v.setAlpha(0.1f);
    }

    private void expandRecycleBin() {
        getValueAnimator().start();
    }

    private void collapseRecycleBin() {
        getValueAnimator().reverse();
    }

    private ValueAnimator getValueAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, getResources().getDimensionPixelSize(R.dimen.dimen_recyle_bin_height));
        animator.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = recycleBin.getLayoutParams();
            layoutParams.height = val;
            recycleBin.setLayoutParams(layoutParams);
        });
        animator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

}
