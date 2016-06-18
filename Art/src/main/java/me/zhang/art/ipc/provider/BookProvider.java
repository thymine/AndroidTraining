package me.zhang.art.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Li on 6/18/2016 10:09 AM.
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "me.zhang.art.BOOK_PROVIDER";

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate: currentThread → " + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: currentThread → " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(TAG, "getType: currentThread → " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Log.i(TAG, "insert: currentThread → " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete: currentThread### " + Thread.currentThread().getName());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update: currentThread### " + Thread.currentThread().getName());
        return 0;
    }
}
