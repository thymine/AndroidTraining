package me.zhang.laboratory

import android.content.Context

class Cs2 {

    companion object {
        const val TAG = "Constants-blue-debug"

        fun getString(context: Context): String {
            return Utils.getDebugString(context) + "-red"
        }
    }

}