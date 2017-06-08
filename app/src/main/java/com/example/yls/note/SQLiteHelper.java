package com.example.yls.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yls on 2017/6/8.
 */

public class SQLiteHelper extends SQLiteOpenHelper{
    private String tableName = "record";
    private Context mContext = null;
    private String sql = "create table if not exists " + tableName +
            "(_id integer primary key autoincrement, " +
            "title varchar," +
            "content text," +
            "time varchar)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
