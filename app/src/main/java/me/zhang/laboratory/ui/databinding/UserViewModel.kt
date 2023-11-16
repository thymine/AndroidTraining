package me.zhang.laboratory.ui.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = User()
    val user = _user

    private val _account = Account()
    val account = _account

    private val _repo = MutableStateFlow(
        Repo(
            "https://dgss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-27/297f5edb1e984613083a2d3cc0c5bb36.png",
            "demo",
            "demo repo desc..."
        )
    )
    val repo = _repo

    private val _isError = MutableStateFlow(true)
    val isError = _isError

    private val _rememberMe = MutableLiveData(true)
    val rememberMe = _rememberMe

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

    fun rememberMeChanged(isChecked: Boolean) {
        _rememberMe.value = isChecked
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

data class Repo(val logo: String, val name: String, val description: String)

class LoginViewModel : BaseObservable() {
    @get:Bindable
    var rememberMe: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.rememberMe)
        }
}