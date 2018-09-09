package me.zhang.workbench.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by zhangxiangdong on 2016/9/22.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginService loginService =
                ServiceGenerator.createService(LoginService.class, "user", "secretpassword");
        final Call<User> call = loginService.basicLogin();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
