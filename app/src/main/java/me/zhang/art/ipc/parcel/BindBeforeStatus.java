package me.zhang.art.ipc.parcel;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Li on 6/16/2016 9:36 PM.
 */
public class BindBeforeStatus implements BindStatus {

    @Override
    public void performAddBook(Context context) {
        Toast.makeText(context, "Bind not ok yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void performGetBookList(Context context) {
        Toast.makeText(context, "Bind not ok yet", Toast.LENGTH_SHORT).show();
    }

}
