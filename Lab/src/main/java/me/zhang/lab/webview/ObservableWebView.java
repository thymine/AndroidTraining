package me.zhang.lab.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Zhang on 12/19/2015 4:21 下午.
 */
public class ObservableWebView extends WebView {

    private OnScrollChangeCallback onScrollChangeCallback;

    public ObservableWebView(Context context, OnScrollChangeCallback callback) {
        this(context);
        this.onScrollChangeCallback = callback;
    }

    public ObservableWebView(Context context) {
        super(context);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeCallback != null)
            onScrollChangeCallback.onWebViewScrollChange(this, l, t, oldl, oldt);
    }

    public OnScrollChangeCallback getOnScrollChangeCallback() {
        return onScrollChangeCallback;
    }

    public void setOnScrollChangCallback(OnScrollChangeCallback onScrollChangeCallback) {
        this.onScrollChangeCallback = onScrollChangeCallback;
    }

    public interface OnScrollChangeCallback {
        void onWebViewScrollChange(WebView v, int l, int t, int oldl, int oldt);
    }
}
