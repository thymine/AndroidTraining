package me.zhang.lock.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Li on 2/28/2016 10:11 PM.
 */
public class DelayUtils {
    private static final String PREF_DELAY = "delay";

    public static boolean saveDelayValue(final Context context, final int delay) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(PREF_DELAY, delay).apply();
        return true;
    }

    public static int getDelayValue(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(PREF_DELAY, 200);
    }

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
