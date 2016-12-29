package com.example.liuhaifeng.rongliandemo.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class MessageopenHelper extends SQLiteOpenHelper {
    public MessageopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE message(name VARCHAR(20),message VARCHAR(80),time VARCHAR(20), ty VARCHAR(5),type VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
