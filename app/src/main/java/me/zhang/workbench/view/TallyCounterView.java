package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/3/22.
 */
public class TallyCounterView extends View implements TallyCounter {

    private static final int MAX_COUNT = 9999;
    private static final String MAX_COUNT_STRING = "9999";
    private final Paint mBackgroundPaint;
    private final Paint mLinePaint;
    private final TextPaint mNumberPaint;
    private final RectF mBackgroundRect;
    private final int mCornerRadius;
    private String mDisplayedCount;
    private int mCount;

    public TallyCounterView(Context context) {
        this(context, null);
    }

    public TallyCounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Set up points for canvas drawing.
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(context, android.R.color.white));
        mLinePaint.setStrokeWidth(1f);

        mNumberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

        // Set the number text size to be 64sp.
        // Translate 64sp
        mNumberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));

        // Allocate objects needed for canvas drawing here.
        mBackgroundRect = new RectF();

        // Initialize drawing measurements.
        mCornerRadius = Math.round(2f * getResources().getDisplayMetrics().density);

        // Do initial count setup.
        setCount(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Paint.FontMetrics fontMetrics = mNumberPaint.getFontMetrics();
        // Measure maximum possible width of text.
        final float maxTextWidth = mNumberPaint.measureText(MAX_COUNT_STRING);
        // Estimate maximum possible height of text.
        final float maxTextHeight = -fontMetrics.top + fontMetrics.bottom;

        // Add padding to maximum width calculation.
        final int desiredWidth = Math.round(maxTextWidth + getPaddingLeft() + getPaddingRight());

        // Add padding to maximum height calculation.
        final int desiredHeight = Math.round(maxTextHeight * 2f + getPaddingTop() + getPaddingBottom());

        // Reconcile size that this view wants to be with the size the parent will let it be.
        final int measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec);
        final int measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec);

        // Store the final measured dimensions.
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * Reconcile a desired size for the view contents with a {@link android.view.View.MeasureSpec}
     * constraint passed by the parent.
     * <p>
     * This is a simplified version of {@link View#resolveSize(int, int)}
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A {@link android.view.View.MeasureSpec} passed by the parent.
     * @return A size that best fits {@code contentSize} while respecting the parent's constraints.
     */
    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Grab canvas dimensions.
        final int canvasWidth = canvas.getWidth();
        final int canvasHeight = canvas.getHeight();

        // Calculate horizontal center.
        final float centerX = canvasWidth * 0.5f;

        // Draw the background.
        mBackgroundRect.set(0f, 0f, canvasWidth, canvasHeight);
        canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);

        // Draw baseline.
        final float baselineY = Math.round(canvasHeight * 0.6f);
        canvas.drawLine(0, baselineY, canvasWidth, baselineY, mLinePaint);

        /* Draw text. */

        // Measure the width of text to display.
        final float textWidth = mNumberPaint.measureText(mDisplayedCount);
        // Figure out an x-coordinate that will center the text in the canvas.
        final float textX = Math.round(centerX - textWidth * 0.5f);
        // Draw.
        canvas.drawText(mDisplayedCount, textX, baselineY, mNumberPaint);
    }

    @Override
    public void reset() {
        setCount(0);
    }

    @Override
    public void increment() {
        setCount(++mCount);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public void setCount(int count) {
        mCount = Math.min(count, MAX_COUNT);
        mDisplayedCount = String.format(Locale.getDefault(), "%04d", count); // e.g. 0001
        invalidate();
    }

}
