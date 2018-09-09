package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
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
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;

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

        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mLinearGradient = new LinearGradient(
                        0, 0, mViewWidth, 0, new int[]{Color.BLUE, 0xffffffff, Color.BLUE}, null,
                        Shader.TileMode.CLAMP
                );
                getPaint().setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }
}
