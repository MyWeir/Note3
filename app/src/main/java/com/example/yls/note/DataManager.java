package com.example.yls.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by yls on 2017/6/8.
 */

public class DataManager {
    private Context mContext=null;
    private SQLiteDatabase mSQLiteDatabase=null;
    private SQLiteHelper dh = null;
    private String dbName = "note.db";
    private int dbVersion=1;
    public DataManager(Context context){
        mContext=context;
    }
    public void open(){
        try{
            dh=new SQLiteHelper(mContext,dbName,null,dbVersion);
            if(dh==null){
                Log.v("msg","is null");
                return;
            }
            mSQLiteDatabase=dh.getWritableDatabase();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(){
        mSQLiteDatabase.close();
        dh.close();
    }

    public Cursor selectAll(){
        Cursor cursor=null;
        try{
            String query="select * from record";
            cursor=mSQLiteDatabase.rawQuery(query,null);
        }catch (Exception e){
            e.printStackTrace();
            cursor=null;
        }
        return  cursor;
    }
    public Cursor selectedId(int id){
        Cursor cursor=null;
        try{
            String query="select * from record where _id='"+id+"'";
            cursor=mSQLiteDatabase.rawQuery(query,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    public long insert(String title,String content){
        long datatime= System.currentTimeMillis();
        long l=-1;
        try{
            ContentValues cv=new ContentValues();
            cv.put("title",title);
            cv.put("content",content);
            cv.put("time",datatime);
            l=mSQLiteDatabase.insert("record",null,cv);
        }catch (Exception e){
            e.printStackTrace();
            l=-1;
        }
        return l;
    }
    public int delete(long id){
        int affect=0;
        try{
            affect=mSQLiteDatabase.delete("record","_id=?",new String[]{id+""});

        }catch (Exception e){
            e.printStackTrace();
            affect=-1;
        }
        return  affect;
    }
    public int update(int id,String title,String content){
        int affect=0;
        try{
            ContentValues cv=new ContentValues();
            cv.put("title",title);
            cv.put("content",content);
            String w[]={id+""};
            affect=mSQLiteDatabase.update("record",cv,"_id=?",w);
        }catch (Exception e){
            e.printStackTrace();
            affect=-1;
        }
        return affect;
    }

}
