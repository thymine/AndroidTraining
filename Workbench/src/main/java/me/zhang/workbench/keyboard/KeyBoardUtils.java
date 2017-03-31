package me.zhang.workbench.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zhangxiangdong on 2017/3/31.
 */
class KeyBoardUtils {


    private static InputMethodManager sInputMethodManager;

    static void hideSoftkeyboard(Activity activity) {
        if (null != activity && null != activity.getCurrentFocus()) {
            if (sInputMethodManager == null) {
                sInputMethodManager = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            if (null != sInputMethodManager) {
                sInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showInputSoft(View view) {
        if (null != view) {
            if (sInputMethodManager == null) {
                sInputMethodManager = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            if (sInputMethodManager != null) {
                sInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

}
