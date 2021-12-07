package me.zhang.laboratory

import android.content.Context

class Cs {

    companion object {
        const val TAG = "Constants-debug"

        fun getString(context: Context): String {
            return Utils.getDebugString(context)
        }
    }

}