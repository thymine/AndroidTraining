package me.zhang.laboratory

import android.content.Context
import com.alibaba.fastjson.JSON

class Cs {

    companion object {
        const val TAG = "Constants-staging"

        fun getString(context: Context): String {
            val cc = Cc("key", "value")
            return "$TAG: ${JSON.toJSONString(cc)}"
        }

    }

}