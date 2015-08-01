package me.zhang.listview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Zhang on 7/26/2015 10:19 下午.
 */
public class LoadingMoreListView extends ListView implements AbsListView.OnScrollListener {

    // ListView 底部加载更多布局
    View footer;

    int lastVisibleItem;
    int totalItemCount;

    boolean isLoading;

    LoadingMoreListener loadingMoreListener;

    public void setLoadingMoreListener(LoadingMoreListener loadingMoreListener) {
        this.loadingMoreListener = loadingMoreListener;
    }

    public LoadingMoreListView(Context context) {
        super(context);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingMoreListView(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
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
        /* 默认隐藏底部加载更多提示 */
        footer.setVisibility(View.GONE);
        /* 注册ListView滚动监听 */
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (lastVisibleItem == totalItemCount) {
                if (!isLoading) {
                    /* 正在加载更多 */
                    isLoading = true;
                    footer.setVisibility(VISIBLE);

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /* 检测回掉接口是否可用 */
                            checkListenerValidation();
                            /* 加载更多数据接口回调 */
                            loadingMoreListener.onLoad();

                            /* 完成加载 */
                            isLoading = false;
                            footer.setVisibility(GONE);
                        }
                    }, 1000);
                }
            }
        }
    }

    private void checkListenerValidation() {
        if (loadingMoreListener == null)
            throw new IllegalArgumentException(
                    LoadingMoreListener.class.getSimpleName() + " 不能为 null"
            );
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    protected interface LoadingMoreListener {
        void onLoad();
    }
}
