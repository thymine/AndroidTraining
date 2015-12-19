package me.zhang.lab.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.zhang.lab.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private ObservableWebView remote;
    private Handler handler = new Handler();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        remote = (ObservableWebView) findViewById(R.id.wv_remote);
        // Enable Javascript
        WebSettings webSettings = remote.getSettings();
        webSettings.setJavaScriptEnabled(true);

        remote.loadUrl("https://www.baidu.com/");

        // Force links and redirects to open in the WebView instead of in a browser
        // remote.setWebViewClient(new WebViewClient());

        // Stop local links and redirects from opening in browser instead of WebView
        remote.setWebViewClient(new CustomWebViewClient());

        remote.setOnScrollChangCallback(new ObservableWebView.OnScrollChangeCallback() {

            @Override
            public void onWebViewScrollChange(WebView v, int l, int t, int oldl, int oldt) {
                @SuppressWarnings("deprecation") int height = (int) Math.floor(v.getContentHeight() * v.getScale());
                int webViewHeight = v.getHeight();
                int cutoff = height - webViewHeight - 10; // Don't be too strict on the cutoff point
                if (t >= cutoff) {
                    jumpToLocalPage();
                }
            }
        });
    }

    private void jumpToLocalPage() {
        startActivity(new Intent(this, LocalPageActivity.class));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                remote.scrollTo(0, 0);
            }
        }, 300);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "url: " + url);

            // Checks for the string "baidu.com" at the end of the host name of the url
            if (Uri.parse(url).getHost().endsWith("baidu.com")) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;

            // Now, a user can click any of the Baidu links and stay within the app,
            // but links to external sites are opened in a browser
        }
    }

    @Override
    public void onBackPressed() {
        if (remote.canGoBack())
            remote.goBack();
        else
            super.onBackPressed();
    }
}
