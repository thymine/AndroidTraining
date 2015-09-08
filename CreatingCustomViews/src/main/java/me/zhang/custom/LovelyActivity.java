package me.zhang.custom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by Zhang on 8/15/2015 8:25 下午.
 */
public class LovelyActivity extends Activity {

    private int[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.LTGRAY
    };
    private Random random = new Random();
    private LovelyView mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lovely);

        mine = (LovelyView) findViewById(R.id.lv_mine);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            /* update the view */
            int circleCol = colors[random.nextInt(7)];
            mine.setCircleCol(circleCol);

            int labelCol = colors[random.nextInt(7)];
            mine.setLabelCol(labelCol);
            mine.setCircleText("My God!");
        }
        return super.onTouchEvent(event);
    }
}
