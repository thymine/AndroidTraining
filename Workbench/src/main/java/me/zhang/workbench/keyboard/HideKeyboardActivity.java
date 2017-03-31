package me.zhang.workbench.keyboard;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import me.zhang.workbench.R;

public class HideKeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_keyboard);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (view instanceof EditText) {
            View innerView =  getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_DOWN &&
                    !getLocationOnScreen(innerView).contains(x, y)) {
                KeyBoardUtils.hideSoftkeyboard(this);
            }
        }

        return handleReturn;
    }

    private Rect getLocationOnScreen(View view) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        view.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + view.getWidth();
        mRect.bottom = location[1] + view.getHeight();

        return mRect;
    }

}
