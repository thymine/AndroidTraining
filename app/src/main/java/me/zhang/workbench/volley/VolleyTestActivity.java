package me.zhang.workbench.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.workbench.R;

public class VolleyTestActivity extends AppCompatActivity {

    @BindView(R.id.button_request_raw)
    Button mRequestRawButton;

    @BindView(R.id.button_request_parsed)
    Button mRequestParsedButton;

    @BindView(R.id.text_response)
    TextView mResponseText;

    private static final String URL =
            "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=7&video=1";
    private Request<String> mRawRequest;
    private Request<BingoResult> mParsedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_request_raw)
    public void requestRaw() {
        if (mRawRequest != null) {
            mRawRequest.cancel();
        }

        mRawRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mResponseText.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseText.setText(R.string.response_error);
                    }
                });
        mRawRequest.setTag(this);

        RequestManager.INSTANCE.addToRequestQueue(mRawRequest);
    }

    @OnClick(R.id.button_request_parsed)
    public void requestParsed() {
        if (mParsedRequest != null) {
            mParsedRequest.cancel();
        }

        mParsedRequest = new GsonRequest<>(URL, BingoResult.class, null,
                new Response.Listener<BingoResult>() {
                    @Override
                    public void onResponse(BingoResult response) {
                        List<Image> images = response.images;
                        StringBuilder builder = new StringBuilder();
                        for (Image image : images) {
                            builder.append(image.copyright);
                            builder.append("\n\n");
                        }
                        mResponseText.setText(builder.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseText.setText(R.string.response_error);
                    }
                }
        );
        mParsedRequest.setTag(this);

        RequestManager.INSTANCE.addToRequestQueue(mParsedRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestQueue singletonQueue = RequestManager.INSTANCE.getRequestQueue();
        singletonQueue.cancelAll(this);
    }
}
