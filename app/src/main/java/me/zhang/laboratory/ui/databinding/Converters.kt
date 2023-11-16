package me.zhang.laboratory.ui.databinding

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.Locale

object DateStringConverter {
    @InverseMethod("stringToDate")
    @JvmStatic
    fun dateToString(
        value: Long
    ): String {
        // Converts long to String.
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(value)
    }

    @JvmStatic
    fun stringToDate(
        value: String
    ): Long {
        // Converts String to long.
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(value)?.time ?: 0L
    }
}