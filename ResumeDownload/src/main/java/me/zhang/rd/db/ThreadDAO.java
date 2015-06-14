package me.zhang.rd.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import me.zhang.rd.entity.ThreadInfo;

/**
 * Created by zhang on 15-6-14 下午3:13.
 * <p>
 * Implementation of data access interface
 */
public class ThreadDAO implements IThreadDAO {

    private DBHelper mHelper;

    public ThreadDAO(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void insertThreadInfo(ThreadInfo info) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id, url, start, end, progress) " +
                "values(?, ?, ?, ?, ?)", new Object[]{
                info.getId(),
                info.getUrl(),
                info.getStart(),
                info.getEnd(),
                info.getProgress()
        });
        db.close();
    }

    @Override
    public void deleteThreadInfo(String url, int threadId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?", new Object[]{
                url,
                threadId
        });
        db.close();
    }

    @Override
    public void updateThreadInfo(String url, int threadId, long progress) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set progress = ? where url = ? and thread_id = ?",
                new Object[]{
                        progress,
                        url,
                        threadId
                });
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreadInfos(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{
                url
        });
        List<ThreadInfo> infos = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadInfo info = new ThreadInfo(
                    cursor.getInt(cursor.getColumnIndex("thread_id")),
                    cursor.getString(cursor.getColumnIndex("url")),
                    cursor.getLong(cursor.getColumnIndex("start")),
                    cursor.getLong(cursor.getColumnIndex("end")),
                    cursor.getLong(cursor.getColumnIndex("progress"))
            );
            infos.add(info);
        }
        cursor.close();
        db.close();
        return infos;
    }

    @Override
    public boolean exists(String url, int threadId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
                new String[]{
                        url,
                        Integer.toString(threadId)
                });
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }

}
