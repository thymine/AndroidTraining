package me.zhang.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zhang on 8/1/2015 1:39 下午.
 */
public class PullToRefreshListView extends LoadingMoreListView {

    View header; // 头部下拉加载更多布局
    int headerHeight; // 顶部布局高度
    int firstVisibleItem;
    boolean isRemark; // 标记，当前是在ListView最顶端按下
    private int startY; // 按下时的Y值

    int state; // 当前状态;
    final int NONE = 0; // 正常状态
    final int PULL = 1; // 下拉刷新
    final int RELEASE = 2; // 松开刷新
    final int REFRESHING = 3; // 正在刷新

    int scrollState; // ListView 滚动状态

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
     *
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                    refreshViewByState();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;

            case MotionEvent.ACTION_UP:
                if (state == RELEASE) {
                    state = REFRESHING;
                    // 加载更多数据
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           refreshComplete();
                        }
                    }, 1000);

                    refreshViewByState();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    refreshViewByState();
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void refreshViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tv_tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.iv_arrow);
        ProgressBar bar = (ProgressBar) header.findViewById(R.id.pb_refreshing);

        /* 箭头动画 */
        RotateAnimation up2down = new RotateAnimation(
                0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        up2down.setDuration(100);
        up2down.setFillAfter(true);
        RotateAnimation down2up = new RotateAnimation(
                180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        down2up.setDuration(100);
        down2up.setFillAfter(true);

        switch (state) {
            case NONE:
                arrow.clearAnimation();
                setTopPadding(-headerHeight);
                break;

            case PULL:
                arrow.setVisibility(VISIBLE);
                bar.setVisibility(GONE);
                tip.setText("下拉刷新");

                arrow.clearAnimation();
                arrow.setAnimation(down2up);
                break;

            case RELEASE:
                arrow.setVisibility(VISIBLE);
                bar.setVisibility(GONE);
                tip.setText("松开刷新");

                arrow.clearAnimation();
                arrow.setAnimation(up2down);
                break;

            case REFRESHING:
                /* 正在刷新固定高度 */
                setTopPadding(headerHeight);

                arrow.clearAnimation();
                arrow.setVisibility(GONE);
                bar.setVisibility(VISIBLE);
                tip.setText("正在刷新");
                break;
        }
    }

    /**
     * 判断移动中的状态
     */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }

        int tempY = (int) ev.getY();
        int space = tempY - startY; // 滑动距离
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    refreshViewByState();
                }
                break;
            case PULL:
                setTopPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELEASE;
                    refreshViewByState();
                }
                break;
            case RELEASE:
                setTopPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    refreshViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    refreshViewByState();
                }
                break;
            case REFRESHING:
                refreshViewByState();
                break;
        }
    }

    /**
     * 完成刷新
     */
    public void refreshComplete() {
        state = NONE;
        isRemark = false;
        refreshViewByState();

        TextView updateTime = (TextView) header.findViewById(R.id.tv_update_time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String time = sdf.format(date);
        updateTime.setText(time);
    }
}
