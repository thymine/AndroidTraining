package me.zhang.lab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义View
 *
 * @author Aige
 * @since 2014/11/19
 */
public class FontView extends View {
    private static final String TAG = FontView.class.getSimpleName();

    private static final String TEXT = "applet宽\tva\"Ja高ξτ\nβбпшㄎㄊ鼃";
    private Paint textPaint, linePaint;// 文本的画笔和中心线的画笔
    private Paint.FontMetrics mFontMetrics;// 文本测量对象

    private int baseX, baseY;// Baseline绘制的XY坐标

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);
        mFontMetrics = textPaint.getFontMetrics();
        Log.d(TAG, "ascent：" + mFontMetrics.ascent);
        Log.d(TAG, "top：" + mFontMetrics.top);
        Log.d(TAG, "leading：" + mFontMetrics.leading);
        Log.d(TAG, "descent：" + mFontMetrics.descent);
        Log.d(TAG, "bottom：" + mFontMetrics.bottom);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 计算Baseline绘制的起点X轴坐标
        baseX = (int) (canvas.getWidth() / 2 - textPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标
//        baseY = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        baseY = canvas.getHeight() / 2;
//        baseY = (int) ((canvas.getHeight() / 2) + ((Math.abs(textPaint.ascent() - Math.abs(textPaint.descent()))) / 2));
        canvas.drawText(TEXT, baseX, baseY, textPaint);

        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, baseY, canvas.getWidth(), baseY, linePaint);

        // 在文本中心处绘制一条线
        float centerOffset = Math.abs((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawLine(0, baseY - centerOffset, canvas.getWidth(), baseY - centerOffset, linePaint);

        // 在文本顶部处绘制一条线
        float ascentOffset = Math.abs(textPaint.ascent());
        canvas.drawLine(0, baseY - ascentOffset, canvas.getWidth(), baseY - ascentOffset, linePaint);

        // 在文本底部处绘制一条线
        float descentOffset = Math.abs(textPaint.descent());
        canvas.drawLine(0, baseY + descentOffset, canvas.getWidth(), baseY + descentOffset, linePaint);
    }
}
