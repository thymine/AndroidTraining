package me.zhang.workbench.view;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by Zhang on 9/5/2017 7:21 PM.
 */
public class AnimatedStateBorderDrawable extends Drawable implements Animatable {

    private static final long FRAME_DURATION = 16; // ms
    private boolean mRunning;
    private long mStartTime;
    private int mAnimDuration;

    private Paint mPaint;
    private ColorStateList mColorStateList;
    private int mPrevColor;
    private int mMiddleColor;
    private int mCurColor;
    private int mBorderWidth;
    private int mBorderRadius;

    private RectF mRectF;
    private Path mPath;

    public AnimatedStateBorderDrawable(ColorStateList colorStateList, int borderWidth,
                                       int borderRadius, int duration) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mRectF = new RectF();

        mColorStateList = colorStateList;
        mCurColor = mColorStateList.getDefaultColor();
        mPrevColor = mCurColor;
        mBorderWidth = borderWidth;
        mBorderRadius = borderRadius;
        mAnimDuration = duration;
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int color = mColorStateList.getColorForState(state, mCurColor);
        if (mCurColor != color) {
            if (mAnimDuration > 0) {
                mPrevColor = isRunning() ? mMiddleColor : mCurColor;
                mCurColor = color;
                start();
            } else {
                mPrevColor = color;
                mCurColor = color;
                invalidateSelf();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mPath.reset();

        mPath.addRect(bounds.left, bounds.top, bounds.right, bounds.bottom, Path.Direction.CW);
        mRectF.set(bounds.left + mBorderWidth, bounds.top + mBorderWidth,
                bounds.right - mBorderWidth, bounds.bottom - mBorderWidth);
        mPath.addRoundRect(mRectF, mBorderRadius, mBorderRadius, Path.Direction.CW);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.setColor(isRunning() ? mMiddleColor : mCurColor);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        stop();
    }

    @Override
    public void scheduleSelf(@NonNull Runnable what, long when) {
        mRunning = true;
        super.scheduleSelf(what, when);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        resetAnimation();
        scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
        invalidateSelf();
    }

    private void resetAnimation() {
        mStartTime = SystemClock.uptimeMillis();
        mMiddleColor = mPrevColor;
    }

    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            update();
        }
    };

    private void update() {
        long curTime = SystemClock.uptimeMillis();
        float progress = Math.min(1f, (float) (curTime - mStartTime) / mAnimDuration);
        mMiddleColor = getMiddleColor(mPrevColor, mCurColor, progress);

        if (progress == 1f) {
            mRunning = false;
        }

        if (isRunning()) {
            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
        }

        invalidateSelf();
    }

    private int getMiddleColor(int prevColor, int curColor, float factor) {
        if (prevColor == curColor)
            return curColor;

        if (factor == 0f)
            return prevColor;
        else if (factor == 1f)
            return curColor;

        int a = getMiddleValue(Color.alpha(prevColor), Color.alpha(curColor), factor);
        int r = getMiddleValue(Color.red(prevColor), Color.red(curColor), factor);
        int g = getMiddleValue(Color.green(prevColor), Color.green(curColor), factor);
        int b = getMiddleValue(Color.blue(prevColor), Color.blue(curColor), factor);

        return Color.argb(a, r, g, b);
    }

    private int getMiddleValue(int prev, int next, float factor) {
        return Math.round(prev + (next - prev) * factor);
    }

    @Override
    public void stop() {
        mRunning = false;
        unscheduleSelf(mUpdater);
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }
}
