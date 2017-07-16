package me.zhang.art.ipc.parcel;

import android.content.Context;

/**
 * Created by Li on 6/16/2016 9:31 PM.
 */
public interface BindStatus {

    void performAddBook(Context context);

    void performGetBookList(Context context);

}
