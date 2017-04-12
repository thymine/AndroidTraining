package com.example.icndb;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangxiangdong on 2017/4/12.
 */
public class JokeFinder {
    private WeakReference<TextView> mTextViewWeakReference;
    private Retrofit retrofit;

    public interface ICNDB {
        @GET("/jokes/random")
        Call<IcndbJoke> getJoke(@Query("firstName") String firstName,
                                @Query("lastName") String lastName,
                                @Query("limitTo") String limitTo);
    }

    public JokeFinder() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getJoke(TextView textView, String first, String last) {
        mTextViewWeakReference = new WeakReference<>(textView);
        new JokeTask().execute(first, last);
    }

    private class JokeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            ICNDB icndb = retrofit.create(ICNDB.class);
            Call<IcndbJoke> icndbJoke = icndb.getJoke(params[0], params[1], "[nerdy]");
            String joke = null;
            try {
                joke = icndbJoke.execute().body().getJoke();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return joke;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = mTextViewWeakReference.get();
            if (textView != null && !TextUtils.isEmpty(result)) {
                textView.setText(result);
            }
        }
    }

}
