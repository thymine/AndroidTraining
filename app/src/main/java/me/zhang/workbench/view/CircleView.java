package me.zhang.workbench.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.workbench.R;

import static android.graphics.Color.RED;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.getMode;
import static android.view.View.MeasureSpec.getSize;
import static java.lang.Math.min;

/**
 * Created by Li on 7/5/2016 8:50 PM.
 */
public class CircleView extends View {

    public static final int DEFAULT_DIMEN = 256;

    private Paint mPaint = new Paint(ANTI_ALIAS_FLAG);

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        final int circleColor = a.getColor(R.styleable.CircleView_circle_color, RED);
        a.recycle();

        mPaint.setColor(circleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = getMode(widthMeasureSpec);
        int widthSpecSize = getSize(widthMeasureSpec);
        int heightSpecMode = getMode(heightMeasureSpec);
        int heightSpecSize = getSize(heightMeasureSpec);

        // set default dimens for "wrap_content"
        if (widthSpecMode == AT_MOST && heightSpecMode == AT_MOST) {
            setMeasuredDimension(DEFAULT_DIMEN, DEFAULT_DIMEN);
        } else if (widthSpecMode == AT_MOST) {
            setMeasuredDimension(DEFAULT_DIMEN, heightSpecSize);
        } else if (heightSpecMode == AT_MOST) {
            setMeasuredDimension(widthSpecSize, DEFAULT_DIMEN);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // do not forget paddings
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight; // remove horizontal paddings
        int height = getHeight() - paddingTop - paddingBottom; // remove vertical paddings

        int radius = min(width, height) / 2;
        canvas.drawCircle(width / 2 + paddingLeft, height / 2 + paddingTop, radius, mPaint);
    }
}
