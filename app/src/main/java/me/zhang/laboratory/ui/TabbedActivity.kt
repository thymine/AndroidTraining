package me.zhang.laboratory.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import me.zhang.laboratory.ui.ui.main.SectionsPagerAdapter

class TabbedActivity : BaseActivity() {

    companion object {
        const val LOG_TAG = "TabbedActivity"

        const val TEXT_SIZE_MIN = 14f
        const val TEXT_SIZE_MAX = 24f

        const val TEXT_SIZE_RANGE = TEXT_SIZE_MAX - TEXT_SIZE_MIN
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(me.zhang.laboratory.R.layout.activity_tabbed)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(me.zhang.laboratory.R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(me.zhang.laboratory.R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.d(LOG_TAG, "p: $position, po: $positionOffset, pop: $positionOffsetPixels")

                val firstTabSize = TEXT_SIZE_MAX - positionOffset * TEXT_SIZE_RANGE
                val firstTabScale = firstTabSize / TEXT_SIZE_MIN
                val firstPageTabView = getTabTextView(position)
                firstPageTabView?.textScaleX = firstTabScale
                firstPageTabView?.scaleY = firstTabScale

                val secondTabSize = TEXT_SIZE_MIN + positionOffset * TEXT_SIZE_RANGE
                val secondTabScale = secondTabSize / TEXT_SIZE_MIN
                val secondPageTabView = getTabTextView(position + 1)
                secondPageTabView?.textScaleX = secondTabScale
                secondPageTabView?.scaleY = secondTabScale
            }

            override fun onPageSelected(position: Int) {
            }

            fun getTabTextView(selectedTabPosition: Int): TextView? {
                val slidingTabIndicator = tabs.getChildAt(0) as LinearLayout?
                val tabView = slidingTabIndicator?.getChildAt(selectedTabPosition) as LinearLayout?
                return tabView?.getChildAt(1) as TextView?
            }

        })
    }
}