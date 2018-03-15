package me.zhang.workbench.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangxiangdong on 2018/3/15.
 */
public class CircleMovingView extends View {

    private static final int WHAT_START = 100;
    private static final int STEP = 10; // px
    private boolean lToR = true;
    private boolean tToB = true;
    private Paint paint;
    private int centerX;
    private int centerY;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_START:
                    invalidate();
                    handler.sendEmptyMessageDelayed(WHAT_START, 16);
                    return true;
            }
            return false;
        }
    });

    public CircleMovingView(Context context) {
        this(context, null);
    }

    public CircleMovingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMovingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int radius = height / 16;

        centerX = lToR ? centerX + STEP : centerX - STEP;
        centerY = tToB ? centerY + STEP : centerY - STEP;

        if (centerX >= width - radius) {
            lToR = false;
        } else if (centerX <= radius) {
            lToR = true;
        }

        if (centerY >= height - radius) {
            tToB = false;
        } else if (centerY <= radius) {
            tToB = true;
        }

        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    public void start() {
        handler.sendEmptyMessage(WHAT_START);
    }

    public void stop() {
        handler.removeMessages(WHAT_START);
    }

}
