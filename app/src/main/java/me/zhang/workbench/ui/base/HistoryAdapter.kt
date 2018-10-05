package me.zhang.workbench.ui.base

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.zhang.workbench.R

class HistoryAdapter(private val histories: List<String>, private val clickListener: OnHistoryItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<HistoryAdapter.VH>() {

    interface OnHistoryItemClickListener {
        fun onClick(history: String)
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

    class VH(itemView: View, private val clickListener: OnHistoryItemClickListener) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private val historyItem = itemView.findViewById<TextView>(R.id.historyItem)

        fun bind(item: String) {
            historyItem.text = item
            historyItem.setOnClickListener { clickListener.onClick(item) }
        }

    }

}