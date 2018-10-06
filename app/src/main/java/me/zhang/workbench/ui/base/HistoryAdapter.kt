package me.zhang.workbench.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.zhang.workbench.R

class HistoryAdapter(private val histories: List<String>, private val clickListener: OnHistoryItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<HistoryAdapter.VH>() {

    interface OnHistoryItemClickListener {
        fun onClick(history: String)
        fun onLongClick(v: View, touchX: Float, touchY: Float)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false), clickListener)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    private fun getItem(position: Int): String {
        return histories[position]
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("ClickableViewAccessibility")
    class VH(itemView: View, private val clickListener: OnHistoryItemClickListener) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private val historyItem: TextView = itemView.findViewById(R.id.historyItem)
        private val lastTouchDownXY = FloatArray(2)

        init {
            this.historyItem.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    lastTouchDownXY[0] = event.x
                    lastTouchDownXY[1] = event.y
                }
                false
            }
        }

        fun bind(item: String) {
            historyItem.text = item
            historyItem.setOnClickListener { clickListener.onClick(item) }
            historyItem.setOnLongClickListener { v ->
                v.tag = item
                clickListener.onLongClick(v, lastTouchDownXY[0], lastTouchDownXY[1])
                true
            }
        }

    }

}