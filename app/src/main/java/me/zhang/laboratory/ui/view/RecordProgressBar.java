package me.zhang.laboratory.ui.view;

import static me.zhang.laboratory.utils.UiUtilsKt.convertDpToPixel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.zhang.laboratory.ui.RecordProgressBarActivity;
import me.zhang.laboratory.ui.bean.PartBean;

public class RecordProgressBar extends View {

    @NonNull
    private final Path roundCornerPath = new Path();
    @NonNull
    private final List<PartBean> drawingParts = new ArrayList<>();
    @NonNull
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    @NonNull
    private final RectF progressRect = new RectF();
    private final int minWidth;
    private final int minHeight;
    private final int dividerWidth;
    private long maxProgress = RecordProgressBarActivity.Const.MAX_PROGRESS;
    private float factor;
    private boolean shouldDrawEndDivider;

    public RecordProgressBar(Context context) {
        this(context, null);
    }

    public RecordProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        minWidth = (int) convertDpToPixel(256, getContext());
        minHeight = (int) convertDpToPixel(6, getContext());
        dividerWidth = (int) convertDpToPixel(2, getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getFinalWidth(widthMeasureSpec), getFinalHeight(heightMeasureSpec));
    }

    private int getFinalWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = minWidth;
        }
        return size;
    }

    private int getFinalHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = minHeight;
        }
        return size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        progressRect.set(0, 0, w, h);

        roundCornerPath.addRoundRect(progressRect, minHeight, minHeight, Path.Direction.CW);

        factor = w * 1f / maxProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(roundCornerPath);
        super.onDraw(canvas);

        // 进度条背景（空进度）
        paint.setColor(Color.parseColor("#2e000000"));
        progressRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(progressRect, paint);

        int partCount = drawingParts.size();
        if (partCount > 0) {
            float left = 0;
            for (int i = 0; i < partCount; i++) {
                PartBean drawingPart = drawingParts.get(i);
                long duration = drawingPart.duration;
                float partWidth = duration * factor;

                paint.setColor(Color.parseColor("#37C2BC"));
                progressRect.set(left, 0, left + partWidth, getMeasuredHeight());
                canvas.drawRect(progressRect, paint);

                left += partWidth;

                if (i < partCount - 1 || shouldDrawEndDivider) {
                    // 绘制白色分割线
                    paint.setColor(Color.WHITE);
                    progressRect.set(
                            left - dividerWidth / 2f,
                            0,
                            left + dividerWidth / 2f,
                            getMeasuredHeight()
                    );
                    canvas.drawRect(progressRect, paint);
                }
            }
        }
    }

    public void setDrawingParts(@NonNull List<PartBean> drawingParts) {
        this.drawingParts.clear();
        this.drawingParts.addAll(drawingParts);
        invalidate();
    }

    public void addDrawingPart(@NonNull PartBean drawingPart) {
        this.drawingParts.add(drawingPart);
        // animateThisPart(drawingPart);
        invalidate();
    }

    private void animateThisPart(@NonNull PartBean drawingPart) {
        long duration = drawingPart.duration;
        drawingPart.duration = 0;
        shouldDrawEndDivider = false;
        CountDownTimer timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                drawingPart.duration = duration - millisUntilFinished;
                invalidate();
            }

            @Override
            public void onFinish() {
                shouldDrawEndDivider = true;
                invalidate();
            }
        };
        timer.start();
    }

    public void setMaxProgress(long maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

}
