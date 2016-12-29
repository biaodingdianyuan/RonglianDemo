package com.example.liuhaifeng.rongliandemo.dao;

/**
 * Created by liuhaifeng on 2016/12/26.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_SENT = 0;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}
