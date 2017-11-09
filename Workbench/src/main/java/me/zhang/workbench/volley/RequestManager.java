package me.zhang.workbench.volley;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import me.zhang.workbench.App;

/**
 * Created by zhangxiangdong on 2017/11/9.
 */
public enum RequestManager {

    @SuppressLint("StaticFieldLeak")INSTANCE(App.getContext());

    private Context mContext;

    RequestManager(Context context) {
        mContext = context;
    }

    private RequestQueue mRequestQueue;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
