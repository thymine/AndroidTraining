package me.zhang.laboratory.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import me.zhang.laboratory.utils.UiUtils;

/**
 * Created by zhangxiangdong on 2019/1/15 15:51.
 */
public class RotaryKnob extends View {

    private static final int WHAT_PROGRESS = 1;
    private static final float PROGRESS_MAX = 100;
    private static final float DEGREE_MAX = 300;

    private final Paint scaleTextPaint = new Paint();
    private final Paint panPaint = new Paint();
    private final Paint panScalePaint = new Paint();

    private final Paint userScalePaint = new Paint();
    private final Paint userPanPaint = new Paint();

    private final Paint titlePaint = new Paint();
    private final Paint subtitlePaint = new Paint();

    private final RectF oval = new RectF();
    private final Rect textBounds = new Rect();
    private final int[] numbers = {0, 25, 50, 75, 100};
    private final float[] positions = {0, 0.24f, 1f};
    private final int[] colors = {Color.parseColor("#0053CAC3"), Color.parseColor("#53CAC3"), Color.parseColor("#53CAC3")};

    private final float fScaleLength;
    private final float fOffset;
    private final float fTextMargin;
    private final float fMinimalDimen;

    private String title = "0段";
    private String subtitle = "XX经历";

    private int step = 0;
    private int progress = 0;
    private Shader gradientPan;

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROGRESS:
                    if (step < progress) {
                        postInvalidate();
                        handler.sendEmptyMessageDelayed(WHAT_PROGRESS, 16 /* ms */); // 线性
                        step += 2;
                    }
                    return true;
            }
            return false;
        }
    };
    private final Handler handler = new Handler(callback);

    public RotaryKnob(Context context) {
        this(context, null);
    }

    public RotaryKnob(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotaryKnob(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scaleTextPaint.setTextSize(UiUtils.convertDpToPixel(14, context));
        scaleTextPaint.setColor(Color.parseColor("#AAAAAA"));
        scaleTextPaint.setAntiAlias(false);

        panPaint.setColor(Color.parseColor("#EBEBEB"));
        panPaint.setStyle(Paint.Style.STROKE);
        panPaint.setStrokeWidth(UiUtils.convertDpToPixel(1, context));

        panScalePaint.setColor(Color.parseColor("#EBEBEB"));
        panScalePaint.setStyle(Paint.Style.STROKE);
        panScalePaint.setStrokeCap(Paint.Cap.ROUND);
        panScalePaint.setStrokeWidth(UiUtils.convertDpToPixel(2, context));

        fScaleLength = UiUtils.convertDpToPixel(20, context);
        fOffset = UiUtils.convertDpToPixel(10, context);
        fTextMargin = UiUtils.convertDpToPixel(50, context);
        fMinimalDimen = UiUtils.convertDpToPixel(256, context);


        userScalePaint.setColor(Color.parseColor("#53CAC3"));
        userScalePaint.setStyle(Paint.Style.STROKE);
        userScalePaint.setStrokeCap(Paint.Cap.ROUND);
        userScalePaint.setStrokeWidth(UiUtils.convertDpToPixel(2, context));

        userPanPaint.setColor(Color.parseColor("#53CAC3"));
        userPanPaint.setStyle(Paint.Style.STROKE);
        userPanPaint.setStrokeWidth(UiUtils.convertDpToPixel(1, context));

        titlePaint.setColor(Color.parseColor("#2DB4B4"));
        titlePaint.setTextSize(UiUtils.convertDpToPixel(24, context));
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        subtitlePaint.setColor(Color.parseColor("#484848"));
        subtitlePaint.setTextSize(UiUtils.convertDpToPixel(18, context));
        subtitlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int finalWidth = getFinalWidth(widthMeasureSpec);
        int finalHeight = getFinalHeight(heightMeasureSpec);
        final int finalDimen = finalWidth > finalHeight ? finalHeight : finalWidth;
        setMeasuredDimension(finalDimen, finalDimen);
    }

    private int getFinalWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = (int) fMinimalDimen; // 最小宽度
        }
        return size;
    }

    private int getFinalHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = (int) fMinimalDimen; // 最小高度
        }
        return size;
    }

    @SuppressLint("Assert")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int knobWidth = getMeasuredWidth();
        int knobHeight = getMeasuredHeight();
        float centerX = knobWidth >> 1;
        float centerY = knobHeight >> 1;
        float scaleY = knobHeight >> 1;

        // **************** 绘制空盘 *****************

        //region 绘制内盘
        float leftInnerPan = fScaleLength + fOffset;
        float topInnerPan = fScaleLength + fOffset;
        float rightInnerPan = knobWidth - leftInnerPan;
        float bottomInnerPan = knobHeight - topInnerPan;
        oval.set(leftInnerPan, topInnerPan, rightInnerPan, bottomInnerPan);
        canvas.drawArc(oval, 120, 300, false, panPaint);
        //endregion

        //region 绘制刻度
        canvas.save();
        canvas.rotate(-60, centerX, centerY);
        for (int i = 0; i <= PROGRESS_MAX; i++) {
            if (i % 2 == 0) { // 绘制线条刻度
                canvas.drawLine(0, scaleY, fScaleLength, scaleY, panScalePaint);
            }
            canvas.rotate(3, centerX, centerY);
        }
        canvas.restore();
        //endregion

        //region 绘制文本刻度
        canvas.save();
        canvas.translate(centerX, centerY);
        float radius = (knobHeight >> 1) - fTextMargin /* 偏移量，用于绘制文本刻度 */;
        int len = numbers.length;
        assert len > 1;
        float avgTheta = DEGREE_MAX / (len - 1);
        for (int i = 0; i < len; i++) {
            int number = numbers[i];
            float theta = 120 + i * avgTheta;
            String numberText = String.valueOf(number);
            textBounds.setEmpty();
            scaleTextPaint.getTextBounds(numberText, 0, numberText.length(), textBounds);
            float x = (float) (radius * Math.cos(Math.PI * ((360 - theta) / 180f)));
            float y = (float) (-radius * Math.sin(Math.PI * ((360 - theta) / 180f)));

            float X = x - (textBounds.width() >> 1);
            float Y = y + (textBounds.height() >> 1);
            canvas.drawText(numberText, X, Y, scaleTextPaint);
        }
        canvas.restore();
        //endregion

        // **************** 绘制进度 *****************

        //region 绘制用户内盘
        canvas.save();
        canvas.rotate(120, centerX, centerY);
        if (gradientPan == null) {
            gradientPan = new SweepGradient(centerX, centerY, colors, positions);
        }
        userPanPaint.setShader(gradientPan);
        canvas.drawArc(oval, 0, getCurrentUserSweepAngle(), false, userPanPaint);
        canvas.restore();
        //endregion

        //region 绘制用户刻度
        canvas.save();
        canvas.rotate(-60, centerX, centerY);
        int scaleCount = progress == 0 ? 0 : (int) (getCurrentUserScaleCount());
        for (int i = 0; i < scaleCount; i++) {
            canvas.save();
            canvas.rotate(i * 6, centerX, centerY);
            if (i <= 12) {
                userScalePaint.setAlpha((int) (i * 1f / 12 * 255));
            }
            canvas.drawLine(0, scaleY, fScaleLength, scaleY, userScalePaint);
            canvas.restore();
        }
        canvas.restore();
        //endregion

        // **************** 绘制内容 *****************

        //region 绘制内容
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(subtitle)) {
            textBounds.setEmpty();
            titlePaint.getTextBounds(title, 0, title.length(), textBounds);
            canvas.drawText(title, centerX - (textBounds.width() >> 1), centerY - (textBounds.height() >> 1) - fTextMargin / 10, titlePaint);

            textBounds.setEmpty();
            subtitlePaint.getTextBounds(subtitle, 0, subtitle.length(), textBounds);
            canvas.drawText(subtitle, centerX - (textBounds.width() >> 1), centerY + (textBounds.height() >> 1) + fTextMargin / 10, subtitlePaint);
        }
        //endregion
    }

    private float getCurrentUserSweepAngle() {
        return 1f * step / PROGRESS_MAX * DEGREE_MAX;
    }

    private float getCurrentUserScaleCount() {
        return 1f * step / PROGRESS_MAX * 50 + 1;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    public void toProgress(@IntRange(from = 0, to = 100) int progress) {
        this.step = 0;
        this.progress = progress;
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessage(WHAT_PROGRESS);
    }

    public void setContent(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        invalidate();
    }

}
