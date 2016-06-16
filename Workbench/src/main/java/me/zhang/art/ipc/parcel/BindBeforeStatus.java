package me.zhang.art.ipc.parcel;

import android.widget.Toast;

import me.zhang.workbench.MainApp;

/**
 * Created by Li on 6/16/2016 9:36 PM.
 */
public class BindBeforeStatus implements BindStatus {

    @Override
    public void performAddBook() {
        Toast.makeText(MainApp.me, "Bind not ok yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void performGetBookList() {
        Toast.makeText(MainApp.me, "Bind not ok yet", Toast.LENGTH_SHORT).show();
    }

}
