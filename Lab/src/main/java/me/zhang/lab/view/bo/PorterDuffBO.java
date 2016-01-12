package me.zhang.lab.view.bo;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * PorterDuffView的业务逻辑对象
 * 不用管我是如何实现的你只管调用即可
 *
 * @author Aige
 * @since 2014/11/19
 */
public class PorterDuffBO implements IBO {
    private static final String TAG = "PorterDuffBO";
    private int size;
    private Point mPoint;

    public PorterDuffBO() {
        mPoint = new Point();
    }

    public void setSize(int size) {
        this.size = size;
        mPoint.set(size, size);
    }

    public Bitmap initSrcBitmap() {
        int[] pixels = new int[mPoint.x * mPoint.y];
        int dst = 0;
        for (int row = 0; row < mPoint.y; ++row) {
            for (int col = 0; col < mPoint.x; ++col) {
                int color = color((float) (mPoint.y - row) / mPoint.y, (float) (mPoint.x - col) / mPoint.x, (float) (mPoint.x - col) / mPoint.x, (float) col / mPoint.x);
//                Log.i(TAG, "[" + row + ", " + col + "] src color: " + color);
                pixels[dst++] = color;
            }
        }
        return Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888);
    }

    public Bitmap initDisBitmap() {
        int[] pixels = new int[mPoint.x * mPoint.y];
        int dst = 0;
        for (int row = 0; row < mPoint.y; ++row) {
            for (int col = 0; col < mPoint.x; ++col) {
                int color = color((float) (mPoint.x - col) / mPoint.x, (float) (mPoint.y - row) / mPoint.x, (float) row / mPoint.y, (float) row / mPoint.y);
//                Log.i(TAG, "[" + row + ", " + col + "] dst color: " + color);
                pixels[dst++] = color;
            }
        }
        return Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888);
    }

    private int color(float Alpha, float R, float G, float B) {
        return (int) (Alpha * 255) << 24 | (int) (R * Alpha * 255) << 16 | (int) (G * Alpha * 255) << 8 | (int) (B * Alpha * 255);
    }
}
