package me.zhang.workbench.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zhang on 2016/6/21 下午 5:35 .
 */
public class SimpleLayout extends ViewGroup {

    public SimpleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int N = getChildCount();
        for (int i = 0; i < N; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int N = getChildCount();

        int currentTop = 0;

        for (int i = 0; i < N; i++) {
            View childView = getChildAt(i);
            childView.layout(
                    0,
                    currentTop,
                    childView.getMeasuredWidth(),
                    childView.getMeasuredHeight() + currentTop
            );
            currentTop += childView.getMeasuredHeight();
        }
    }
}
