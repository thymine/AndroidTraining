package me.zhang.workbench.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by zhangxiangdong on 2016/10/21.
 */
public class TagDrawable extends Drawable {

    private Paint mBackgroundPaint;
    private Paint mTagNamePaint;
    private String mTagName;

    public TagDrawable(int tagBackgroundColor, String tagName) {
        this(tagBackgroundColor, tagName, Color.WHITE);
    }

    public TagDrawable(int tagBackgroundColor, String tagName, int tagColor) {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(tagBackgroundColor);
        mBackgroundPaint.setStrokeWidth(3);

        mTagNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTagNamePaint.setColor(tagColor);
        mTagNamePaint.setTextAlign(Paint.Align.LEFT);
        mTagNamePaint.setTextSize(48);
        mTagNamePaint.setStrokeWidth(3);

        this.mTagName = tagName;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect r = getBounds();
        canvas.drawRect(r, mBackgroundPaint);

        int cHeight = r.height();
        int cWidth = r.width();
        mTagNamePaint.getTextBounds(mTagName, 0, mTagName.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(mTagName, x, y, mTagNamePaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mBackgroundPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mBackgroundPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
