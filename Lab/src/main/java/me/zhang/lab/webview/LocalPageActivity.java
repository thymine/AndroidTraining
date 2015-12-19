package me.zhang.lab.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.zhang.lab.R;

public class LocalPageActivity extends AppCompatActivity {

    private ObservableWebView local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_page);

        local = (ObservableWebView) findViewById(R.id.wv_local);

        local.loadUrl("file:///android_asset/content.html");
        local.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().length() == 0)
                    return false;
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        });

        local.setOnScrollChangCallback(new ObservableWebView.OnScrollChangeCallback() {

            @Override
            public void onWebViewScrollChange(WebView v, int l, int t, int oldl, int oldt) {
                int height = (int) Math.floor(v.getContentHeight() * v.getScale());
                int webViewHeight = v.getHeight();
                int cutoff = height - webViewHeight - 10; // Don't be too strict on the cutoff point
                if (t >= cutoff) {
                    finish();
                }
            }
        });
    }
}
