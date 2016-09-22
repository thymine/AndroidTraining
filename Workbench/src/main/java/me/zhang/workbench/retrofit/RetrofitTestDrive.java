package me.zhang.workbench.retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by zhangxiangdong on 2016/9/22.
 */
public class RetrofitTestDrive {

    public static void main(String[] args) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);

        // Fetch and print a list of the contributors to this library.
        Call<List<Contributor>> call = client.contributors("zhangxiangdong", "AndroidLab");

        List<Contributor> contributors = new ArrayList<>();

        try {
            System.out.println("-Requesting...");
            contributors.addAll(call.execute().body());
        } catch (IOException ignored) {

        }

        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
        System.out.println("-Done.");
    }

}
