package com.example.liuhaifeng.rongliandemo.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/26.
 */

public class data {

    private String [] name={"11111111111","122222222222","1332333333333"};
    private String [] id={"8018313400000007","8018313400000008","8018313400000009"};
    List<UserDao> list=new ArrayList<UserDao>();
    UserDao user;
    public  List<UserDao> getdata(){
        for(int i=0;i<name.length;i++){
            user=new UserDao();
            user.setName(name[i]);
            user.setId(id[i]);
            list.add(user);
        }
        return  list;
    }
}
