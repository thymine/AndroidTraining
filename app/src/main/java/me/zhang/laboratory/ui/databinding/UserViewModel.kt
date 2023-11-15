package me.zhang.laboratory.ui.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = User()
    val user = _user

    private val _account = Account()
    val account = _account

    private val _repo = MutableStateFlow(Repo("demo", "demo repo desc..."))
    val repo = _repo

    init {
        viewModelScope.launch {
            _repo.value = Repository.loadRepo()
        }
    }

    fun setUser(name: String, age: Int, skills: ArrayList<String>) {
        _user.name.set(name)
        _user.age.set(age)
        _user.skills.addAll(skills)
    }

    fun setAccount(username: String, password: String) {
        _account.username = username
        _account.password = password
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

class Account : BaseObservable() {
    @get:Bindable
    var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @get:Bindable
    var password: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
}

data class Repo(val name: String, val description: String)