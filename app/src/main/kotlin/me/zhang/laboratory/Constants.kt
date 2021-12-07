package me.zhang.laboratory

import android.content.Context

class Constants {

    companion object {
        const val TAG: String = Cs.TAG

        fun getString(context: Context): String {
            return Cs.getString(context)
        }
    }

}