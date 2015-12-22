package me.zhang.lab.popup;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/12/22 下午 1:38 .
 */
public class PopUpActivity extends Activity {

    ImageView pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);
        pizza = (ImageView) findViewById(R.id.iv_pizza);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ImageView popup = new ImageView(this);
        final PopupWindow popupWindow = new PopupWindow(popup, 128, 128);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popup.setImageResource(R.drawable.popup);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        rect.offset((int) event.getX(), (int) event.getY());

        int offX = rect.left - 128;
        int offY = rect.top - 128;
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, offX, offY);
        return super.onTouchEvent(event);
    }
}
