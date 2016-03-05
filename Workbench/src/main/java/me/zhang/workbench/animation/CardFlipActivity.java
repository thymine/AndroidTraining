package me.zhang.workbench.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import me.zhang.workbench.R;

/**
 * Created by Li on 3/5/2016 9:21 PM.
 */
public class CardFlipActivity extends Activity implements View.OnClickListener, Animator.AnimatorListener {

    public static final int DELAY_MILLIS = 1000;
    private PopupWindow popupWindow;
    private ProgressBar pbLoading;

    private View popupView;
    private Button btnShowPop;
    private Button btnSubmit;
    private AnimatorSet animatorSet;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

        btnShowPop = (Button) findViewById(R.id.btn_showpop);
        btnShowPop.setOnClickListener(this);

        popupView = LayoutInflater.from(this).inflate(R.layout.layout_popup, null);
        btnSubmit = (Button) popupView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        pbLoading = (ProgressBar) popupView.findViewById(R.id.pb_loading);

        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_out);
        animatorSet.addListener(this);
        animatorSet.setTarget(btnSubmit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_showpop:
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;

            case R.id.btn_submit:
                animatorSet.start();
                break;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
