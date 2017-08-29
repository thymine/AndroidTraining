package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.workbench.R;
import me.zhang.workbench.utils.UiUtils;

/**
 * Created by Zhang on 2016/7/4 上午 9:34 .
 */
public class CustomView extends View {

    private static final int DEFAULT_SQUARE_SIZE = 720; // px
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Canvas mCanvas;
    private Paint mPaint = new Paint();

    {
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(UiUtils.convertDpToPixel(6, getContext()));
    }

    {
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.droid_64px);

        Bitmap immutable = BitmapFactory.decodeResource(getResources(), R.drawable.droid_96px);
        mBitmap2 = immutable.copy(Bitmap.Config.ARGB_8888, true);
        mCanvas = new Canvas(mBitmap2);
        setOnClickListener(new OnClickListener() {

            private final static String TEXT_DROID = "Android";
            private float mCurrentAngle;

            @Override
            public void onClick(View v) {
                int xCanvas = mCanvas.getWidth() / 2;
                int yCanvas = mCanvas.getHeight() / 2;

                float textWidth = mPaint.measureText(TEXT_DROID);
                float xPosition = xCanvas - textWidth / 2;
                float yPosition = (int) (yCanvas - ((mPaint.descent() + mPaint.ascent()) / 2));

                mCanvas.save();
                mCanvas.rotate(mCurrentAngle, xCanvas, yCanvas);
                mCanvas.drawText(TEXT_DROID, xPosition, yPosition, mPaint);
                mCanvas.restore();
                invalidate();

                mCurrentAngle += 5;
            }
        });
    }

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSquare(widthMeasureSpec), measureSquare(heightMeasureSpec));
    }

    private int measureSquare(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DEFAULT_SQUARE_SIZE;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap1, 0, 0, null);

        float xPosition = canvas.getWidth() - mBitmap2.getWidth();
        float yPosition = canvas.getHeight() - mBitmap2.getHeight();
        canvas.drawBitmap(mBitmap2, xPosition, yPosition, null);
    }

}
