package me.zhang.lab.input;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.zhang.lab.R;

import static android.view.View.GONE;

public class InputActivity extends AppCompatActivity {

    private LinearLayout ll_pic;
    private LinearLayout ll_root;
    private Handler handler = new Handler();
    private RelativeLayout rl_trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        ll_pic = (LinearLayout) findViewById(R.id.ll_pic);
        rl_trigger = (RelativeLayout) findViewById(R.id.rl_trigger);
        ll_root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                ll_root.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = ll_root.getRootView().getHeight() - rect.bottom;
                if (rootInvisibleHeight > 200) { // 键盘弹起
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ll_pic.setVisibility(GONE);
                        }
                    });
                } else { // 键盘消失
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        });

        rl_trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_pic.setVisibility(View.VISIBLE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LayoutTransition lt = new LayoutTransition();
//            lt.setDuration(400);
//            lt.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
//            lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
//            lt.disableTransitionType(LayoutTransition.APPEARING);
            lt.disableTransitionType(LayoutTransition.DISAPPEARING);
            lt.enableTransitionType(LayoutTransition.CHANGING);
            ll_root.setLayoutTransition(lt);
        }

    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 18) {
            // When SDK Level >= 18, the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    private void adjustWindowSoftInputMode() {
//        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        View currentFocus = getCurrentFocus();
//        if (fl_selector.getVisibility() == View.VISIBLE) {
//            fl_selector.setVisibility(View.GONE);
//            getWindow()
//                    .setSoftInputMode(
//                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//                                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            // 打开软键盘
//            if (currentFocus != null)
//                mInputMethodManager.showSoftInput(currentFocus, InputMethodManager.SHOW_FORCED);
//        } else {
//            fl_selector.setVisibility(View.VISIBLE);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//            // 隐藏软键盘
//            if (currentFocus != null)
//                mInputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
//        }
    }
}
