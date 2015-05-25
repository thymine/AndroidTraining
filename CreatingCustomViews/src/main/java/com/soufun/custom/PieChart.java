package com.soufun.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zhang on 2015/5/16 下午 5:50 .
 */
public class PieChart extends View {

    private boolean mShowText;
    private int mTextPos;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0,
                0
        );
        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showTxt, false);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
            // Note that TypedArray objects are a shared resource and must be recycled after use.
            a.recycle();
        }
    }

    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
        requestLayout();
    }

}
