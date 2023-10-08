package me.zhang.laboratory.ui.net

import me.zhang.laboratory.ui.room.User
import retrofit2.Call
import retrofit2.http.GET

interface NameFakeService {

    @GET("/")
    fun getFakeUser(): Call<User>

}