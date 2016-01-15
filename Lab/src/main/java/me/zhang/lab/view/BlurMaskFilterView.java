package me.zhang.lab.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.zhang.lab.R;
import me.zhang.lab.utils.HardwareUtils;

/**
 * BlurMaskFilter
 *
 * @author Aige
 * @since 2014/11/23
 */
@SuppressLint("NewApi")
public class BlurMaskFilterView extends View {

    private static final String TAG = BlurMaskFilterView.class.getSimpleName();

    private Paint shadowPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap srcBitmap, shadowBitmap;// 位图和阴影位图

    private int x, y;// 位图绘制时左上角的起点坐标

    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 记得设置模式为SOFTWARE
        setLayerType(LAYER_TYPE_SOFTWARE, null);

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
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(Color.DKGRAY);
        shadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
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
        x = dimens[0] / 4;
        y = dimens[1] / 4;

        // 获取位图
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a, options);
        /* 原始位图宽和高 */
        final float h = options.outHeight;
        final float w = options.outWidth;
        Log.i(TAG, "w: " + w);
        Log.i(TAG, "h: " + h);
        options.inJustDecodeBounds = false;
        float ratio = w > h ? (w > dimens[0] ? w / dimens[0] : dimens[0] / w) : (h > dimens[1] ? h / dimens[1] : dimens[1] / h);
        Log.i(TAG, "ratio: " + ratio);
        int inSampleSize = (int) Math.ceil(ratio);
        Log.i(TAG, "inSampleSize: " + inSampleSize);
        options.inSampleSize = inSampleSize;

        srcBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a, options);

        // 获取位图的Alpha通道图
        shadowBitmap = srcBitmap.extractAlpha();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 先绘制阴影
        canvas.drawBitmap(shadowBitmap, x, y, shadowPaint);

        // 再绘制位图
        canvas.drawBitmap(srcBitmap, x, y, null);
    }
}
