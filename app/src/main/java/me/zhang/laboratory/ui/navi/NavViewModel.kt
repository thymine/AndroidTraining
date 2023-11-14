package me.zhang.laboratory.ui.navi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class NavViewModel : ViewModel() {

    private val _dslEnabled = MutableLiveData(false)
    val dslEnabled = _dslEnabled

    open fun setDslEnabled(enabled: Boolean) {
        _dslEnabled.value = enabled
    }

}