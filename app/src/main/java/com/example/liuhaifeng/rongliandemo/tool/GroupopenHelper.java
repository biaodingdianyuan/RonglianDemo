package com.example.liuhaifeng.rongliandemo.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class GroupopenHelper extends SQLiteOpenHelper {
    public GroupopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE group_data(groupid VARCHAR(20),name VARCHAR(20),declare VARCHAR(80),owner VARCHAR(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
