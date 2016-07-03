package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import me.zhang.workbench.R;

/**
 * Created by Li on 7/3/2016 4:01 PM.
 */
public class ScrollViewActivity extends AppCompatActivity {

    private static final String TAG = ScrollViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        assert scrollView != null;
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //noinspection deprecation
                scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                Log.i(TAG, "onGlobalLayout: ScrollView#getMeasuredHeight = " + scrollView.getMeasuredHeight());
                Log.i(TAG, "onGlobalLayout: ScrollView#getHeight = " + scrollView.getHeight());

                Log.i(TAG, "onGlobalLayout: Child#getMeasuredHeight = " + scrollView.getChildAt(0).getMeasuredHeight());
                Log.i(TAG, "onGlobalLayout: Child#getHeight = " + scrollView.getChildAt(0).getHeight());
            }
        });
    }
}
