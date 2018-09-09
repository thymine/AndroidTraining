package me.zhang.workbench.utils

/**
 * Created by Zhang on 3/4/2018 4:32 PM.
 */
class ListUtils {

    companion object {
        fun <T> getCount(list: List<T>?): Int {
            var count = 0
            if (list != null) {
                count = list.size
            }
            return count
        }

        fun <T> getItem(list: List<T>?, index: Int): T? {
            var item: T? = null
            if (list != null) {
                item = list[index]
            }
            return item
        }
    }

}

