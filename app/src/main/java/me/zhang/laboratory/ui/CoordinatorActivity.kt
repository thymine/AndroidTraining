package me.zhang.laboratory.ui

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_coordinator.*
import me.zhang.laboratory.R

class CoordinatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_coordinator)

        val fakeData = arrayListOf<Bean>()
        fakeData.apply {
            for (i in 1..100) {
                add(Bean(R.mipmap.ic_launcher, getString(R.string.string_demo_title) + " -> $i", getString(R.string.string_demo_desc)))
            }
        }

        val bsb = BottomSheetBehavior.from<LinearLayout>(findViewById(R.id.bottomSheet))

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnTouchListener, View.OnClickListener {

            override fun onClick(v: View) {
                Toast.makeText(applicationContext, fakeData[adapterPosition].name, Toast.LENGTH_SHORT).show()
            }

            init {
                itemView.setOnTouchListener(this)
                itemView.setOnClickListener(this)
            }

            val expandRunnable = ExpandRunnable()
            val collapsRunnable = HideRunnable()

            val longPressTimeout = ViewConfiguration.getLongPressTimeout().toLong()

            var actionDownTime = 0L
            var lastMotionX = 0F
            var lastMotionY = 0F

            val viewLocation = IntArray(2)
            val viewRect = Rect()

            inner class ExpandRunnable : Runnable {
                override fun run() {
                    if (bsb.state != BottomSheetBehavior.STATE_EXPANDED) {
                        itemView.parent.requestDisallowInterceptTouchEvent(true)
                        bsb.state = BottomSheetBehavior.STATE_EXPANDED

                        // TODO Replace To Your Own Logic As Needed.
                        cardTitleText.text = fakeData[adapterPosition].name
                    }
                }
            }

            inner class HideRunnable : Runnable {
                override fun run() {
                    if (bsb.state != BottomSheetBehavior.STATE_HIDDEN) {
                        bsb.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }

            }

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val action = event.action
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        actionDownTime = System.currentTimeMillis()
                        v.postDelayed(expandRunnable, longPressTimeout)

                        lastMotionX = event.rawX
                        lastMotionY = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val interactTime = System.currentTimeMillis() - actionDownTime
                        if (interactTime < longPressTimeout) {
                            v.removeCallbacks(expandRunnable)
                        } else {
                            if (bsb.state == BottomSheetBehavior.STATE_EXPANDED) {
                                dispatchMoveEventToCard(event)
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)

                        val interactTime = System.currentTimeMillis() - actionDownTime
                        if (interactTime >= longPressTimeout) {
                            v.post(collapsRunnable)
                        } else {
                            if (interactTime < ViewConfiguration.getTapTimeout()) {
                                v.performClick()
                            }
                            if (interactTime < longPressTimeout) {
                                v.removeCallbacks(expandRunnable)
                            }
                        }
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)

                        val interactTime = System.currentTimeMillis() - actionDownTime
                        if (interactTime < longPressTimeout) {
                            v.removeCallbacks(expandRunnable)
                        }
                    }
                }
                return true
            }

            @SuppressLint("SetTextI18n")
            private fun dispatchMoveEventToCard(event: MotionEvent) {
                val rawX = event.rawX
                val rawY = event.rawY

                val isWithinLeftFab = isWithinView(leftFab, rawX, rawY)
                if (isWithinLeftFab) {
                    if (!leftFab.isPressed) {
                        leftFab.isPressed = true

                        // TODO Callback Here As Needed.
                        cardTitleText.text = "RED ←"
                    }
                } else {
                    if (leftFab.isPressed) {
                        leftFab.isPressed = false

                        // TODO Callback Here As Needed.
                        cardTitleText.text = "RED →"
                    }
                }

                val isWithinRightFab = isWithinView(rightFab, rawX, rawY)
                if (isWithinRightFab) {
                    if (!rightFab.isPressed) {
                        rightFab.isPressed = true

                        // TODO Callback Here As Needed.
                        cardTitleText.text = "BLUE ←"
                    }
                } else {
                    if (rightFab.isPressed) {
                        rightFab.isPressed = false

                        // TODO Callback Here As Needed.
                        cardTitleText.text = "BLUE →"
                    }
                }

                if (isWithinView(cardRecycler, rawX, rawY)
                        && (!isWithinLeftFab && !isWithinRightFab)) {
                    val dx = lastMotionX - rawX
                    val dy = lastMotionY - rawY
                    if (Math.abs(dx) < Math.abs(dy)) {
                        cardRecycler.apply {
                            post { scrollBy(0, dy.toInt()) } // TODO Scroll Your List As Needed.
                        }
                    }
                }

                lastMotionX = event.rawX
                lastMotionY = event.rawY
            }

            private fun isWithinView(targetView: View, rawX: Float, rawY: Float): Boolean {
                targetView.getLocationOnScreen(viewLocation)
                targetView.getGlobalVisibleRect(viewRect)
                return viewRect.contains(rawX.toInt(), rawY.toInt())
            }

            val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
            val titleText: TextView = itemView.findViewById(R.id.titleText)
            val descText: TextView = itemView.findViewById(R.id.descText)
        }

        val adapter = object : RecyclerView.Adapter<VH>() {
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

        val demoRecycler = findViewById<RecyclerView>(R.id.mainRecycler)
        demoRecycler.adapter = adapter

        val cardRecycler = findViewById<RecyclerView>(R.id.cardRecycler)
        cardRecycler.adapter = adapter
    }

    data class Bean(@DrawableRes val profile: Int, val name: String, val desc: String)

}

