package com.example.liuhaifeng.rongliandemo.dao;

import com.yuntongxun.ecsdk.im.ECGroup;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class GroupDao extends ECGroup {
    private String name;//群名
    private String declare;//公告
    private ECGroup.Scope scope;//群组类别
    private ECGroup.Permission permission;//群组验证权限
    private String owner;//创建者
    private Boolean isdiscuss;//是否为讨论组



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeclare() {
        return declare;
    }

    public void setDeclare(String declare) {
        this.declare = declare;
    }

    public ECGroup.Scope getScope() {
        return scope;
    }

    public void setScope(ECGroup.Scope scope) {
        this.scope = scope;
    }

    public ECGroup.Permission getPermission() {
        return permission;
    }

    public void setPermission(ECGroup.Permission permission) {
        this.permission = permission;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getIsdiscuss() {
        return isdiscuss;
    }

    public void setIsdiscuss(Boolean isdiscuss) {
        this.isdiscuss = isdiscuss;
    }
}
