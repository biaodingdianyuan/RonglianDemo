package com.example.liuhaifeng.rongliandemo.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuhaifeng on 2016/12/28.
 */

public class ContactopenHelper extends SQLiteOpenHelper {

    public ContactopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contact(id VARCHAR(20),name VARCHAR(20),listmessage VARCHAR(80),time VARCHAR(20),img_url VARCHAR(20),ty VARCHAR(5))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
