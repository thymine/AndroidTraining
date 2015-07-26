package me.zhang.listview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Zhang on 7/26/2015 10:19 下午.
 */
public class LoadingMoreListView extends ListView {

    // ListView 底部加载更多布局
    View footer;

    public LoadingMoreListView(Context context) {
        super(context);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingMoreListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public LoadingMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public LoadingMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        footer = LayoutInflater.from(context).inflate(R.layout.item_footer, null);
        this.addFooterView(footer);
    }

}
