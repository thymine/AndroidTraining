package me.zhang.bmps.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zhang on 4/24/2015 10:16 下午.
 */
public class BitmapUtils {

    private static final int IO_BUFFER_SIZE = 1024 * 8;

    public static Bitmap decodeSampledBitmapFromInputStream(InputStream in,
                                                            int reqWidth, int reqHeight) {
        InputStream copyOfIn = null;
        try {
            // Image datas
            byte[] datas = inputStreamToBytes(in);

            // First decode with inJustDecodeBounds = true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            in = bytesToInputStream(datas); // Re-create InputStream
            BitmapFactory.decodeStream(in, null, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            // Re-create InputStream
            copyOfIn = bytesToInputStream(datas);
            // Decode bitmap with inSampleSize set.
            return BitmapFactory.decodeStream(copyOfIn, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (copyOfIn != null) {
                    copyOfIn.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean copy(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static InputStream bytesToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static byte[] inputStreamToBytes(InputStream in) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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
