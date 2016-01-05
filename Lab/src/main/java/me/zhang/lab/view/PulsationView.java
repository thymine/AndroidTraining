package me.zhang.lab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.zhang.lab.utils.HardwareUtils;

/**
 * Created by Zhang on 2015/12/24 上午 10:51 .
 */
public class PulsationView extends View implements Runnable {
    private static final String TAG = PulsationView.class.getSimpleName();

    private int direction = -1;

    private Paint paint;

    private Context context;
    private int cx, cy; // 圆环圆心坐标

    private int radius; // 圆环半径

    public PulsationView(Context context) {
        super(context);
    }

    public PulsationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        int[] dimens = new int[2];
        HardwareUtils.getScreenDimens(context, dimens);
        cx = dimens[0] / 2;
        cy = dimens[1] / 2;
        Log.i(TAG, "cx: " + cx + ", cy: " + cy);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /*
         * 设置画笔样式为描边
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        paint.setStyle(Paint.Style.FILL);

        // 设置画笔颜色为自定义颜色
        paint.setColor(Color.argb(255, 255, 128, 103));

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        paint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
        canvas.drawLine(0, cy, 2 * cx, cy, paint);
        canvas.drawLine(cx, 0, cx, 2 * cy, paint);
    }

    public synchronized void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    @Override
    public void run() {
        /*
         * 确保线程不断执行不断刷新界面
         */
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                if (direction < 0) {
                    if (radius <= 1200) {
                        radius += 5;
                    } else {
                        direction = 1;
                    }
                } else {
                    if (radius >= 0) {
                        radius -= 5;
                    } else {
                        direction = -1;
                    }
                }

                // 刷新View
                postInvalidate();

                // 每执行一次暂停30毫秒
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
