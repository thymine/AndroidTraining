package me.zhang.workbench.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static me.zhang.workbench.utils.UiUtils.getScreenDimens;

/**
 * Created by Zhang on 2016/7/5 上午 10:03 .
 * <p/>
 * 自定义一个ViewGroup让其子View在屏幕中间位置横向排列
 */
public class CustomLayout extends ViewGroup {

    private int[] dimens = {480, 800}; // default screen dimens

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            getScreenDimens((Activity) context, dimens);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0; // current child left
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            // child top
            int height = dimens[1] / 2 - childHeight / 2;

            // layout child
            childView.layout(left, height, left + childWidth, height + childHeight);

            // move to next child
            left += childWidth;
        }
    }
}
