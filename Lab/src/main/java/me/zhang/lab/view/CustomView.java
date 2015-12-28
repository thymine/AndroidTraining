package me.zhang.lab.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.zhang.lab.R;
import me.zhang.lab.utils.HardwareUtils;

/**
 * Created by Zhang on 2015/12/24 上午 10:51 .
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();

    private Paint paint;
    private Context context;
    private int cx, cy; // 圆环圆心坐标

    private int radius; // 圆环半径
    private Bitmap bitmap;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        int[] dimens = new int[2];
        HardwareUtils.getScreenDimens(context, dimens);
        cx = dimens[0] / 2;
        cy = dimens[1] / 2;
        Log.i(TAG, "cx: " + cx + ", cy: " + cy);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pizza);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /*
         * 设置画笔样式为描边
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        paint.setStyle(Paint.Style.FILL);

        // 设置画笔颜色为自定义颜色
        paint.setColor(Color.argb(255, 255, 128, 103));

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        paint.setStrokeWidth(10);

        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
//        colorMatrix = new ColorMatrix(new float[]{
//                0.5f, 0, 0, 0, 0,
//                0, 0.5f, 0, 0, 0,
//                0, 0, 0.5f, 0, 0,
//                0, 0, 0, 1, 0,
//        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, 400, paint);
        canvas.drawLine(0, cy, 2 * cx, cy, paint);
        canvas.drawLine(cx, 0, cx, 2 * cy, paint);

        canvas.drawBitmap(bitmap, cx - bitmap.getWidth() / 2, cy - bitmap.getHeight() / 2, paint);
    }

    public synchronized void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

}
