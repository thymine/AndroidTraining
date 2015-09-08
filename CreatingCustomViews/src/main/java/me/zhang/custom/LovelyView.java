package me.zhang.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zhang on 8/15/2015 8:25 下午.
 */
public class LovelyView extends View {

    /* circle and text colors */
    private int circleCol, labelCol;

    /* label text */
    private String circleText;

    /* paint for drawing custom view */
    private Paint circlePaint;

    public int getCircleCol() {
        return circleCol;
    }

    public int getLabelCol() {
        return labelCol;
    }

    public String getCircleText() {
        return circleText;
    }

    public void setCircleCol(int newColor) {
        /* update the instance variable */
        circleCol = newColor;
        /* redraw the view */
        invalidate();
        requestLayout();
    }

    public void setLabelCol(int newColor) {
        labelCol = newColor;
        invalidate();
        requestLayout();
    }

    public void setCircleText(String newLabel) {
        circleText = newLabel;
        invalidate();
        requestLayout();
    }

    public LovelyView(Context context) {
        super(context);
    }

    public LovelyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /* paint object for drawing in onDraw */
        circlePaint = new Paint();

        /* get the attributes specified in attrs.xml using the name we inclued */
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView,
                0, 0);

        try {
            /* get the text and colors specified using the names in attrs.xml */
            circleText = array.getString(R.styleable.LovelyView_circleLabel);
            circleCol = array.getInteger(R.styleable.LovelyView_circleCol, 0); // 0 is default
            labelCol = array.getInteger(R.styleable.LovelyView_labelCol, 0);
        } finally {
            array.recycle();
        }
    }

    public LovelyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LovelyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /* draw the view */

        /* get half of the width and height as we are working with a circle */
        int viewWidthHalf = getMeasuredWidth() / 2;
        int viewHeightHalf = getMeasuredHeight() / 2;

        /* get the radius as half of the with or height, whichever is smaller */
        /* subtract ten so that it has some space around it */
        int radius;
        if (viewWidthHalf > viewHeightHalf)
            radius = viewHeightHalf - 10;
        else
            radius = viewWidthHalf - 10;

        /* set some properties for painting with: */
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        /* set the paint color using the circle color specified */
        circlePaint.setColor(circleCol);

        /* draw circle */
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

        /* set the text color using the color specified */
        circlePaint.setColor(labelCol);
        /* set text properties */
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(50);

        /* draw the text using the string attribute and chosen properties */
        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
    }
}
