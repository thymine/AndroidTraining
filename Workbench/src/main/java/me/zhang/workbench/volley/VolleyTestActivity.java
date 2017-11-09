package me.zhang.workbench.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.workbench.R;

public class VolleyTestActivity extends AppCompatActivity {

    @BindView(R.id.button_request)
    Button mRequestButton;

    @BindView(R.id.button_request_json)
    Button mRequestJsonButton;

    @BindView(R.id.text_response)
    TextView mResponseText;

    private static final String URL = "https://www.baidu.com/";
    private static final String URL_JSON =
            "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=7&video=1";
    private Request<String> mStringRequest;
    private Request<JSONObject> mJSONObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_request)
    public void doRequest() {
        if (mStringRequest != null) {
            mStringRequest.cancel();
        }

        mStringRequest = new StringRequest(Request.Method.GET, URL,
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
        mStringRequest.setTag(this);

        RequestManager.INSTANCE.addToRequestQueue(mStringRequest);
    }

    @OnClick(R.id.button_request_json)
    public void doRequestJson() {
        if (mJSONObjectRequest != null) {
            mJSONObjectRequest.cancel();
        }

        mJSONObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_JSON, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResponseText.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseText.setText(R.string.response_error);
                    }
                }
        );
        mJSONObjectRequest.setTag(this);

        RequestManager.INSTANCE.addToRequestQueue(mJSONObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestQueue singletonQueue = RequestManager.INSTANCE.getRequestQueue();
        singletonQueue.cancelAll(this);
        singletonQueue.stop();
    }
}
