package me.zhang.laboratory.ui.databinding

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _user = User()
    val user = _user

    fun setUser(name: String, age: Int, skills: ArrayList<String>) {
        _user.name.set(name)
        _user.age.set(age)
        _user.skills.addAll(skills)
    }

    fun onClickUser(user: User) {
        _user.name.set(user.name.get())
        _user.age.set(user.age.get() + 1)
    }
}

class User {
    val name = ObservableField<String>()
    val age = ObservableInt()
    val skills = ObservableArrayList<String>()
}