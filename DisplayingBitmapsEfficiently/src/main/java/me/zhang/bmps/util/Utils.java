package me.zhang.bmps.util;

import android.os.Build;

/**
 * Created by zhang on 15-5-3 下午3:01.
 */
public class Utils {

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

}
