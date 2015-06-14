package me.zhang.rd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhang on 15-6-14 下午2:56.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final String TABLE_NAME = "thread_info";

    private static final int VERSION = 1;
    private static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME +
            "(_id integer primary key autoincrement, " +
            "thread_id integer, url text, start integer, end integer, progress integer)";
    private static final String SQL_DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
    }
}
