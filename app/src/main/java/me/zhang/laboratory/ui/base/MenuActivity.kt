package me.zhang.laboratory.ui.base

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.Menu
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.base.HistoryAdapter.OnHistoryItemClickListener
import java.util.SortedMap
import java.util.TreeMap

/**
 * Created by Zhang on 5/11/2015 14:20 下午.
 */
abstract class MenuActivity : AppCompatActivity(), OnItemClickListener,
    SearchView.OnQueryTextListener, OnHistoryItemClickListener {

    private lateinit var mAdapter: ArrayAdapter<String>
    private val historyKeys: MutableList<String> = ArrayList()
    private val actions: SortedMap<String, Intent> = TreeMap()

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var recycleBin: ImageView
    private lateinit var historyList: RecyclerView

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val key = parent.getItemAtPosition(position) as String
        clickItem(key)
    }

    private fun clickItem(key: String) {
        saveKey(key)
        val intent = actions[key]
        if (intent != null) {
            startActivity(intent)
        } else {
            removeKey(key)
            notifyHistoriesChanged()
            Toast.makeText(this, "Invalid entry.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveKey(key: String) {
        historyKeys.remove(key)
        historyKeys.add(0, key)
        saveHistoryKeys()
    }

    private fun removeKey(key: String) {
        historyKeys.remove(key)
        saveHistoryKeys()
    }

    private fun saveHistoryKeys() {
        val histories =
            Gson().toJson(historyKeys.subList(0, getToIndex(historyKeys))) // 最多保存10个历史记录
        val editor = getSharedPreferences(NAME_HISTORIES, MODE_PRIVATE).edit()
        editor.putString(KEY_HISTORIES, histories)
        editor.apply()
    }

    private fun restoreHistoryKeys() {
        val prefs = getSharedPreferences(NAME_HISTORIES, MODE_PRIVATE)
        val restoredHistories = prefs.getString(KEY_HISTORIES, null)
        val historyList = Gson().fromJson<List<String>>(
            restoredHistories,
            object : TypeToken<List<String?>?>() {}.type
        )
        if (historyList != null) {
            historyKeys.clear()
            historyKeys.addAll(historyList.subList(0, getToIndex(historyList))) // 最多展示10个历史记录
        }
    }

    private fun getToIndex(sourceList: List<String>): Int {
        return if (sourceList.size > TO_INDEX) TO_INDEX else sourceList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyHistoriesChanged() {
        historyAdapter.notifyDataSetChanged()
        historyList.visibility = if (shouldShowHistoryPan()) View.VISIBLE else View.GONE
    }

    private fun shouldShowHistoryPan(): Boolean {
        return historyKeys.size > 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        recycleBin = findViewById(R.id.recycleBin)
        setDragListener()
        historyList = findViewById(R.id.historyList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        historyList.layoutManager = layoutManager
        historyAdapter = HistoryAdapter(historyKeys, this)
        historyList.adapter = historyAdapter
        val list = findViewById<ListView>(android.R.id.list)
        list.emptyView = findViewById(android.R.id.empty)
        list.onItemClickListener = this
        list.isFastScrollEnabled = true

//        setTitle(R.string.lab); // define title inside AndroidManifest.xml
        prepareMenu()
        val keys = actions.keys.toTypedArray()
        mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, keys)
        list.adapter = mAdapter
    }

    private fun setDragListener() {
        recycleBin.setOnDragListener { v: View, event: DragEvent ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> return@setOnDragListener event.clipDescription.hasMimeType(
                    ClipDescription.MIMETYPE_TEXT_PLAIN
                )

                DragEvent.ACTION_DRAG_ENTERED -> {
                    (v as ImageView).setColorFilter(Color.RED)
                    return@setOnDragListener true
                }

                DragEvent.ACTION_DRAG_LOCATION -> return@setOnDragListener true
                DragEvent.ACTION_DRAG_EXITED -> {
                    (v as ImageView).clearColorFilter()
                    return@setOnDragListener true
                }

                DragEvent.ACTION_DROP -> {
                    val item = event.clipData.getItemAt(0)
                    val historyItem = item.text
                    removeKey(historyItem.toString())
                    notifyHistoriesChanged()
                    Toast.makeText(this, "History item '$historyItem' removed.", Toast.LENGTH_SHORT)
                        .show()
                    (v as ImageView).clearColorFilter()
                    return@setOnDragListener true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    (v as ImageView).clearColorFilter()
                    Log.d(TAG, "Drop result: " + event.result)
                    collapseRecycleBin()
                    (event.localState as View).alpha = 1.0f
                    return@setOnDragListener true
                }

                else -> Log.e(TAG, "Unknown action type received by OnDragListener.")
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        restoreHistoryKeys()
        notifyHistoriesChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu, this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(this)
        searchView.setOnQueryTextFocusChangeListener { _: View?, hasFocus: Boolean ->
            historyList.visibility =
                if (!hasFocus && shouldShowHistoryPan()) View.VISIBLE else View.GONE
        }

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)
        return true
    }

    protected fun addMenuItem(label: String, cls: Class<*>?) {
        actions[label] = Intent(this, cls)
    }

    protected abstract fun prepareMenu()
    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.i(TAG, "onQueryTextChange: $newText")
        mAdapter.filter.filter(newText)
        return true
    }

    override fun onClick(history: String) {
        clickItem(history)
    }

    override fun onLongClick(v: View, touchX: Float, touchY: Float) {
        val tag = v.tag as CharSequence
        val item = ClipData.Item(tag)
        val dragData = ClipData(tag, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        @Suppress("DEPRECATION")
        v.startDrag(dragData, object : DragShadowBuilder(v) {
            override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
                super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
                outShadowTouchPoint[touchX.toInt()] = touchY.toInt()
            }
        }, v, 0)
        expandRecycleBin()
        v.alpha = 0.1f
    }

    private fun expandRecycleBin() {
        valueAnimator.start()
    }

    private fun collapseRecycleBin() {
        valueAnimator.reverse()
    }

    private val valueAnimator: ValueAnimator
        get() {
            val animator = ValueAnimator.ofInt(
                0,
                resources.getDimensionPixelSize(R.dimen.dimen_recyle_bin_height)
            )
            animator.addUpdateListener { animation: ValueAnimator ->
                val `val` = animation.animatedValue as Int
                val layoutParams = recycleBin.layoutParams
                layoutParams.height = `val`
                recycleBin.layoutParams = layoutParams
            }
            animator.duration =
                resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            animator.interpolator = AccelerateDecelerateInterpolator()
            return animator
        }

    companion object {
        private val TAG = MenuActivity::class.java.simpleName
        const val KEY_HISTORIES = "key_histories"
        private const val NAME_HISTORIES = "name_histories"
        const val TO_INDEX = 10
    }
}