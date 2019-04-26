package com.example.severn.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Sqlite", factory, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Grade (gid integer primary key autoincrement,grade text,time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
