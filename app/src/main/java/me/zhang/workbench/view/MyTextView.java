package me.zhang.workbench.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Li on 6/22/2016 10:59 AM.
 */
public class MyTextView extends TextView {

    private static final String TAG = "MyTextView";

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        Log.i(TAG, String.format("onScrollChanged: mScrollX: %d, mScrollY: %d, oldX: %d, oldY: %d", horiz, vert, oldHoriz, oldVert));
    }

}
