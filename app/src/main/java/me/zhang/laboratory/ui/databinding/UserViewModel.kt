package me.zhang.laboratory.ui.databinding

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _user = ObservableField(User("Zhang", 31))
    val user = _user

    fun onClickUser(user: User) {
        _user.set(User(user.name, user.age.inc()))
    }
}

data class User(val name: String, val age: Int)