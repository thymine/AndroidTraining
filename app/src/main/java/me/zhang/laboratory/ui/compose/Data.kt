package me.zhang.laboratory.ui.compose

import androidx.compose.runtime.mutableStateOf

data class Message(val author: String, val body: String) {
    private val _isExpanded = mutableStateOf(false)

    fun isExpand(): Boolean {
        return _isExpanded.value
    }

    fun toggle() {
        _isExpanded.value = !_isExpanded.value
    }
}