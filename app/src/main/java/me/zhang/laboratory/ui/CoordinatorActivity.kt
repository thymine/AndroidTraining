package me.zhang.laboratory.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.zhang.laboratory.R

class CoordinatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_coordinator)

        val demoRecycler = findViewById<RecyclerView>(R.id.demoRecycler)

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
            val titleText: TextView = itemView.findViewById(R.id.titleText)
            val descText: TextView = itemView.findViewById(R.id.descText)
        }

        data class Bean(@DrawableRes val profile: Int, val name: String, val desc: String)

        val fakeData = arrayListOf<Bean>()
        fakeData.apply {
            for (i in 1..100) {
                add(Bean(R.mipmap.ic_launcher, getString(R.string.string_demo_title), getString(R.string.string_demo_desc)))
            }
        }

        demoRecycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            val dividerPaint = Paint()
            val verticalOffset = resources.getDimensionPixelSize(R.dimen.dimen_demo_padding)

            init {
                dividerPaint.color = getColor(R.color.gray)
                dividerPaint.flags = Paint.ANTI_ALIAS_FLAG
            }

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                val childCount = parent.childCount
                for (i in 0 until childCount) {
                    val child = parent[i]
                    c.drawLine(parent.paddingLeft.toFloat(), child.bottom.toFloat() + verticalOffset,
                            (parent.width - parent.paddingRight).toFloat(), child.bottom.toFloat() + verticalOffset, dividerPaint)
                }
            }

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = verticalOffset
                outRect.bottom = verticalOffset
            }
        })
        demoRecycler.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                return VH(layoutInflater.inflate(R.layout.item_demo, parent, false))
            }

            override fun getItemCount(): Int {
                return fakeData.size
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                val bean = fakeData[position]
                holder.profileImage.setImageDrawable(ContextCompat.getDrawable(applicationContext, bean.profile))
                holder.titleText.text = bean.name
                holder.descText.text = bean.desc
            }

        }
    }


}

class FABAwareScrollingViewBehavior : AppBarLayout.ScrollingViewBehavior {

    constructor() : super()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is FloatingActionButton
                || super.layoutDependsOn(parent, child, dependency)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        val dependencies = coordinatorLayout.getDependencies(child)
        dependencies.forEach {
            if (it is FloatingActionButton) {
                if (dyConsumed > 0) it.hide() else if (dyConsumed < 0) it.show()
                return@forEach
            }
        }
    }

}
