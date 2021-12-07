package me.zhang.laboratory;

import android.content.Context;
import androidx.annotation.NonNull;

public class Utils {

    @NonNull
    public static String getDebugString(@NonNull Context context) {
        return context.getResources().getString(R.string.debug_string);
    }

}
