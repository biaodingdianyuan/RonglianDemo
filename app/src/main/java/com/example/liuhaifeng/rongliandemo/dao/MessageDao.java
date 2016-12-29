package com.example.liuhaifeng.rongliandemo.dao;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class MessageDao {
    private String name;
    private String message;
    private String time;
    private String ty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }
}
