package me.zhang.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zhang on 8/1/2015 1:39 下午.
 */
public class PullToRefreshListView extends LoadingMoreListView {

    View header; // 头部下拉加载更多布局
    int headerHeight; // 顶部布局高度

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public PullToRefreshListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        header = LayoutInflater.from(context).inflate(R.layout.item_header, null);
        /* 添加header布局到ListView头部 */
        addHeaderView(header);

        measureView(header);
        /* 获取头部占用高度 */
        headerHeight = header.getMeasuredHeight();
        Log.d("Zhang", "Header Height: " + headerHeight);
        setTopPadding(-headerHeight);
    }

    /**
     * 通知父布局占用宽高
     * @param view 子view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null)
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        int width = getChildMeasureSpec(0, 0, lp.width);
        int height;
        int tempHeight = lp.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    /**
     * 设置header布局上边距
     *
     * @param topPadding top padding
     */
    private void setTopPadding(int topPadding) {
        header.setPadding(
                header.getPaddingLeft(),
                topPadding,
                header.getPaddingRight(),
                header.getPaddingBottom()
        );
        header.invalidate();
    }


}
