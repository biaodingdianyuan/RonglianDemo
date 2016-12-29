package com.example.liuhaifeng.rongliandemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.liuhaifeng.rongliandemo.dao.ContactDao;
import com.example.liuhaifeng.rongliandemo.tool.MessageopenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/23.
 */

public class MyAPP extends Application {
    private static MyAPP instance;
    public static String name;
    public  static List<ContactDao> Contact_list;


    @Override
    public void onCreate() {
        super.onCreate();
        name=null;
        Contact_list=new ArrayList<ContactDao>();


    }
    /**
     * 单例，返回一个实例
     * @return
     */
    public static MyAPP getInstance() {
        if (instance == null) {

        }
        return instance;
    }
}
