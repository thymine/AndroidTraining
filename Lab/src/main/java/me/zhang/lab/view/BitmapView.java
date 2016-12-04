package me.zhang.lab.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/12/24 上午 10:51 .
 */
public class BitmapView extends View {

    private boolean isChecked;// 用来标识控件是否被点击过
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private Paint paint6;
    private Paint paint7;
    private Paint paint8;
    private Paint paint9;
    private Paint[] paints = new Paint[9];
    private Paint paint10;

    @SuppressWarnings("deprecation")
//    private AvoidXfermode avoidXfermode;// AV模式

    private Context context;
    private Bitmap bitmap;

    public BitmapView(Context context) {
        super(context);
    }

    public BitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pizza, options);

        /*
         * 当画布中有跟0XFFFFFFFF色不一样的地方时候才“染”色
         */
//        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.TARGET);
//        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.AVOID);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[0] = paint1;
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[1] = paint2;
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[2] = paint3;
        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[3] = paint4;
        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[4] = paint5;
        paint6 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[5] = paint6;
        paint7 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[6] = paint7;
        paint8 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[7] = paint8;
        paint9 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints[8] = paint9;
        paint10 = new Paint(Paint.ANTI_ALIAS_FLAG);

        /*
         * 设置画笔样式为描边
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        paint1.setStyle(Paint.Style.FILL);

        // 设置画笔颜色为自定义颜色
        paint1.setColor(Color.argb(255, 255, 128, 103));

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        paint1.setStrokeWidth(10);

        // 生成色彩矩阵
        final ColorMatrix colorMatrix1 = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix2 = new ColorMatrix(new float[]{
                0.5f, 0, 0, 0, 0,
                0, 0.5f, 0, 0, 0,
                0, 0, 0.5f, 0, 0,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix3 = new ColorMatrix(new float[]{
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix4 = new ColorMatrix(new float[]{
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix5 = new ColorMatrix(new float[]{
                0, 0, 1, 0, 0,
                0, 1, 0, 0, 0,
                1, 0, 0, 0, 0,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix6 = new ColorMatrix(new float[]{
                0.393F, 0.769F, 0.189F, 0, 0,
                0.349F, 0.686F, 0.168F, 0, 0,
                0.272F, 0.534F, 0.131F, 0, 0,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix7 = new ColorMatrix(new float[]{
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                0, 0, 0, 1, 0,
        });
        final ColorMatrix colorMatrix8 = new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0, -0.03F,
                -0.062F, 1.378F, -0.016F, 0, 0.05F,
                -0.062F, -0.122F, 1.483F, 0, -0.02F,
                0, 0, 0, 1, 0,
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    paint1.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));
                    paint2.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
                    paint3.setColorFilter(new ColorMatrixColorFilter(colorMatrix3));
                    paint4.setColorFilter(new ColorMatrixColorFilter(colorMatrix4));
                    paint5.setColorFilter(new ColorMatrixColorFilter(colorMatrix5));
                    paint6.setColorFilter(new ColorMatrixColorFilter(colorMatrix6));
                    paint7.setColorFilter(new ColorMatrixColorFilter(colorMatrix7));
                    paint8.setColorFilter(new ColorMatrixColorFilter(colorMatrix8));
                    paint9.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
                    isChecked = true;
                } else {
                    for (Paint p : paints) {
                        p.setColorFilter(new ColorMatrixColorFilter(colorMatrix1));
                    }
                    isChecked = false;
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint1);
        canvas.drawBitmap(bitmap, bitmap.getWidth(), 0, paint2);
        canvas.drawBitmap(bitmap, bitmap.getWidth() * 2, 0, paint3);

        canvas.drawBitmap(bitmap, 0, bitmap.getHeight(), paint4);
        canvas.drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), paint5);
        canvas.drawBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight(), paint6);

        canvas.drawBitmap(bitmap, 0, bitmap.getHeight() * 2, paint7);
        canvas.drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight() * 2, paint8);
        canvas.drawBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, paint9);

        canvas.drawBitmap(bitmap, 0, bitmap.getHeight() * 3, paint10);
        // “染”什么色是由我们自己决定的
        paint10.setARGB(255, 211, 53, 243);

        // 设置AV模式
//        paint10.setXfermode(avoidXfermode);

        // 画一个位图大小一样的矩形
        canvas.drawRect(0, bitmap.getHeight() * 3, bitmap.getWidth(), bitmap.getHeight() * 4, paint10);
    }

}
