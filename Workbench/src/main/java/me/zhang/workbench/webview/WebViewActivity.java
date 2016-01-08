package me.zhang.workbench.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import me.zhang.workbench.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private Button buttonTip;
    private CountDownTimer timer;

    private static final long COUNT_DOWN_INTERVAL = 1000;
    private static final long MILLIS_IN_FUTURE = 3 * 1000;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webview);
        buttonTip = (Button) findViewById(R.id.btn_tip);

        webView.setWebChromeClient(new WebChromeClient());
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);

        webView.loadUrl("file:///android_asset/index.html");

    }

    private void openSpike() {
        String id = "spike";
        String content = "呵呵";
        webView.loadUrl("javascript:repl('" + id + "','" + content + "');");
    }

    public void alert(View view) {
        cancelTimer();
        timer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                buttonTip.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                buttonTip.setText(R.string.text_alert);
                openSpike();
            }
        }.start();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    public void perform(View view) {
        webView.loadUrl("javascript:alert('perform');");
    }
}
