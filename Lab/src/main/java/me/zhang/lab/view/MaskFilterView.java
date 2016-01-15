package me.zhang.lab.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.zhang.lab.utils.HardwareUtils;

/**
 * Created by Zhang on 2016/1/13 上午 11:20 .
 */
public class MaskFilterView extends View {
    private static final String TAG = MaskFilterView.class.getSimpleName();
    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用

    private int left, top, right, bottom;//

    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFF603811);

        // 设置画笔遮罩滤镜
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.INNER));

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 初始化资源
     */
    private void initRes() {
        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         */
        int[] dimens = new int[2];
        HardwareUtils.getScreenDimens(mContext, dimens);

        left = dimens[0] / 4;
        top = dimens[1] / 4;
        right = dimens[0] * 3 / 4;
        bottom = dimens[1] * 3 / 4;

        Log.i(TAG, "width: " + dimens[0]);
        Log.i(TAG, "height: " + dimens[1]);

        Log.i(TAG, "left: " + left);
        Log.i(TAG, "top: " + top);
        Log.i(TAG, "right: " + right);
        Log.i(TAG, "bottom: " + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        // 画一个矩形
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
