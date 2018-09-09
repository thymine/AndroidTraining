package me.zhang.workbench.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.workbench.utils.UiUtilsKt;

/**
 * Created by zhangxiangdong on 2018/3/15.
 */
public class CoordinateView extends View {

    private static final int RECT_COUNT = 5;
    private static final int CIRCLE_COUNT = 16;
    private static final int SCALE_COUNT = 12;

    private static final int TRANSLATE_X = 10; // px
    private static final int ONE_HOUR_DEGREES = 30;
    private static final int WHAT_MOVE_DOT = 100;

    private final Paint paint;
    private final Paint dotPaint;
    private final Rect rect = new Rect();
    private float pointX;
    private boolean isAnimating;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_MOVE_DOT:
                    invalidate();
                    handler.sendEmptyMessageDelayed(WHAT_MOVE_DOT, 16);
                    return true;
            }
            return false;
        }
    });

    public CoordinateView(Context context) {
        this(context, null);
    }

    public CoordinateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.YELLOW);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setStrokeWidth(UiUtilsKt.dp(3));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    stop();
                } else {
                    start();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 高度设置为宽度的2倍
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * 2, widthMode);

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width = getMeasuredWidth();
        final int U = width / RECT_COUNT;

        // 绘制水平排列的正方形
        canvas.save();
        paint.setColor(Color.RED);
        for (int i = 0; i < RECT_COUNT; i++) {
            rect.set(0, 0, U - TRANSLATE_X, U - TRANSLATE_X);
            canvas.drawRect(rect, paint);
            canvas.translate(U, 0);
        }
        canvas.restore();

        final int verticalMargin = U / 8;

        // 绘制同心圆
        canvas.translate(0, U + verticalMargin);
        canvas.save();
        paint.setColor(Color.GREEN);
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            canvas.drawCircle(U, U, U, paint);
            canvas.drawPoint(U, U, paint);

            canvas.scale(0.9f, 0.9f, U, U);
        }
        canvas.restore();

        // 绘制圆盘刻度
        canvas.translate(0, 2 * U + verticalMargin);
        canvas.save();
        paint.setColor(Color.BLUE);
        // 绘制圆盘
        canvas.drawCircle(U, U, U, paint);
        // 绘制圆心
        canvas.drawPoint(U, U, paint);
        // 刻度的长度
        final int scaleLength = U / 12;
        for (int i = 0; i < SCALE_COUNT; i++) {
            canvas.drawLine(U, scaleLength, U, 0, paint);
            canvas.rotate(ONE_HOUR_DEGREES, U, U);
        }
        canvas.restore();

        // 绘制正弦运动
        canvas.translate(0, 2 * U + verticalMargin);
        canvas.save();
        paint.setColor(Color.GRAY);
        final int axis = U / 2;
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, axis, width, axis, paint);
        canvas.drawLine(0, U, width, U, paint);

        final float dotX = (pointX += 10) % width;
        final float dotY = (float) (axis + Math.sin(Math.PI / 300 * pointX) * axis);
        canvas.drawPoint(dotX, dotY, dotPaint);
        canvas.restore();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    private void start() {
        handler.sendEmptyMessage(WHAT_MOVE_DOT);
        isAnimating = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void stop() {
        handler.removeMessages(WHAT_MOVE_DOT);
        isAnimating = false;
    }
}
