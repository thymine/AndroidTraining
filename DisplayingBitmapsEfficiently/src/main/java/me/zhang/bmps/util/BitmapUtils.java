package me.zhang.bmps.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Zhang on 4/24/2015 10:16 下午.
 */
public class BitmapUtils {

    private static final int IO_BUFFER_SIZE = 1024;

    public static Bitmap decodeSampledBitmapFromInputStream(InputStream in,
                                                            int reqWidth, int reqHeight) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Make a copy of InputStream for Bitmap decoding
        copy(in, out);
        // InputSteam for Bitmap decoding
        ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());

        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        // Decode bitmap with inSampleSize set.
        return BitmapFactory.decodeStream(bais, null, options);
    }

    private static int copy(InputStream in, ByteArrayOutputStream out) {
        byte[] buffer = new byte[IO_BUFFER_SIZE];
        int count = 0;
        int n;
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw width and height of image
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // width and height larger than the requested width and height
            while ((halfWidth / inSampleSize) > reqWidth
                    && (halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
