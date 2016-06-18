package me.zhang.art.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import me.zhang.art.ipc.provider.db.DBHelper;

import static me.zhang.art.ipc.provider.db.DBHelper.BOOK_TABLE_NAME;
import static me.zhang.art.ipc.provider.db.DBHelper.USER_TABLE_NAME;

/**
 * Created by Li on 6/18/2016 10:09 AM.
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "me.zhang.art.BOOK_PROVIDER";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BOOK_TABLE_NAME);
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USER_TABLE_NAME);

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, BOOK_TABLE_NAME, BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, USER_TABLE_NAME, USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDatabase; // data source

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate: currentThread → " + Thread.currentThread().getName());

        mContext = getContext();
        initProviderData(); // for demo purpose, don't do time-consuming work here
        return true;
    }

    private void initProviderData() { // insert mock data
        mDatabase = new DBHelper(mContext).getWritableDatabase();

        mDatabase.execSQL("DELETE FROM " + BOOK_TABLE_NAME);
        mDatabase.execSQL("INSERT INTO " + BOOK_TABLE_NAME + " values(1, 'Android')");
        mDatabase.execSQL("INSERT INTO " + BOOK_TABLE_NAME + " values(2, 'Html5')");
        mDatabase.execSQL("INSERT INTO " + BOOK_TABLE_NAME + " values(3, 'Node.js')");
        mDatabase.execSQL("INSERT INTO " + BOOK_TABLE_NAME + " values(4, 'SQL')");
        mDatabase.execSQL("INSERT INTO " + BOOK_TABLE_NAME + " values(5, 'Spring')");

        mDatabase.execSQL("DELETE FROM " + USER_TABLE_NAME);
        mDatabase.execSQL("INSERT INTO " + USER_TABLE_NAME + " values(1, 'Jack', 1)");
        mDatabase.execSQL("INSERT INTO " + USER_TABLE_NAME + " values(2, 'Rose', 0)");
        mDatabase.execSQL("INSERT INTO " + USER_TABLE_NAME + " values(3, 'Tom', 1)");
        mDatabase.execSQL("INSERT INTO " + USER_TABLE_NAME + " values(4, 'Jim', 1)");
        mDatabase.execSQL("INSERT INTO " + USER_TABLE_NAME + " values(5, 'Lily', 0)");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: currentThread → " + Thread.currentThread().getName());

        String table = getTableName(uri);
        return mDatabase.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
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

        String table = getTableName(uri);
        mDatabase.insert(table, null, values);

        // notify dataset changed
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete: currentThread### " + Thread.currentThread().getName());

        String table = getTableName(uri);
        int count = mDatabase.delete(table, selection, selectionArgs);

        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update: currentThread### " + Thread.currentThread().getName());

        String table = getTableName(uri);
        int row = mDatabase.update(table, values, selection, selectionArgs);

        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = USER_TABLE_NAME;
                break;
        }
        if (tableName == null) // no table found, maybe something wrong with the uri
            throw new IllegalArgumentException("Unsupported Uri: " + uri);
        return tableName;
    }

}
