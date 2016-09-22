package me.zhang.workbench.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by zhangxiangdong on 2016/9/22.
 */

public interface LoginService {

    @POST("/login")
    Call<User> basicLogin();

}
