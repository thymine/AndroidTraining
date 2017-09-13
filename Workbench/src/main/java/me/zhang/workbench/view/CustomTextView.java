package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/9/13.
 */
public class CustomTextView extends AppCompatTextView {

    private Paint mBorderPaint;
    private Paint mBackgroundPaint;
    private RectF mRectF = new RectF();

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(getResources().getDimension(R.dimen.text_border_width));

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

        canvas.drawRect(mRectF, mBackgroundPaint);

        super.onDraw(canvas);

        canvas.drawRect(mRectF, mBorderPaint);
    }
}
