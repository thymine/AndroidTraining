package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Color.RED;
import static me.zhang.workbench.utils.UiUtils.convertDpToPixel;

/**
 * Created by Zhang on 2016/7/4 上午 9:34 .
 */
public class CustomView extends View {

    private Paint mPaint;
    private String mHello = "Hello, world!";

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setTextSize(convertDpToPixel(18, context));
        mPaint.setColor(RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw "Hello, world!" string here
        canvas.drawText(mHello, getWidth() / 2 - mPaint.measureText(mHello) / 2, getHeight() / 2, mPaint);
    }
}
