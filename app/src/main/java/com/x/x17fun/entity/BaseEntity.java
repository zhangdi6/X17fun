package com.x.x17fun.entity;

// Created by Ardy on 2020/5/8.

import com.baidu.mapapi.model.LatLng;

public class BaseEntity {
    private String title;
    private String subtitle;
    private String url;
    private String time;
    private String str1;
    private LatLng pt;
    private String str2;
    private String str3;


    private int resource;
    private int mipmap;
    private int type;
    private int count;

    private boolean isCheck;

    public BaseEntity(String title, String subtitle, String url, String time, String str1, String str2, String str3, int resource, int mipmap, int type, int count, boolean isCheck) {
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
        this.time = time;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.resource = resource;
        this.mipmap = mipmap;
        this.type = type;
        this.count = count;
        this.isCheck = isCheck;
    }



    public BaseEntity() {
        super();
    }

    public BaseEntity(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public BaseEntity(String title, String subtitle, int resource, int mipmap, boolean isCheck) {

        this.title = title;
        this.subtitle = subtitle;
        this.resource = resource;
        this.mipmap = mipmap;
        this.isCheck = isCheck;
    }

    public LatLng getPt() {
        return pt;
    }

    public void setPt(LatLng pt) {
        this.pt = pt;
    }

    public BaseEntity(int mipmap, String title, int count) {
        this.title = title;
        this.mipmap = mipmap;
        this.count = count;
    }


    public BaseEntity(int mipmap, String title, String time, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.mipmap = mipmap;
        this.time = time;
    }

    public BaseEntity(int mipmap, String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.mipmap = mipmap;
    }

    public BaseEntity(String title, String url, int resource, int mipmap, int type, int count) {
        this.title = title;
        this.url = url;
        this.resource = resource;
        this.mipmap = mipmap;
        this.type = type;
        this.count = count;

    }

    public BaseEntity(String time, String title, String url, String str1, String str2, boolean isCheck) {
        this.title = title;
        this.url = url;
        this.time = time;
        this.str1 = str1;
        this.str2 = str2;
        this.isCheck = isCheck;
    }

    public BaseEntity(String title, String subtitle, String url, int resource, int mipmap, int type, int count) {
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
        this.resource = resource;
        this.mipmap = mipmap;
        this.type = type;
        this.count = count;
    }

    public BaseEntity(String title, int mipmap, int type, boolean isCheck, int resource) {
        this.title = title;
        this.resource = resource;
        this.mipmap = mipmap;
        this.type = type;
        this.isCheck = isCheck;
    }

    public BaseEntity(int type, String title, String str1, String str2, String str3,String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.type = type;

    }

    public BaseEntity(String title) {
        this.title = title;
    }

    public BaseEntity(String title, String subtitle, String str1) {
        this.title = title;
        this.subtitle = subtitle;
        this.str1 = str1;
    }

    public BaseEntity(String title, String subtitle, String str1, LatLng pt) {
        this.title = title;
        this.subtitle = subtitle;
        this.str1 = str1;
        this.pt = pt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStr1() {
        return str1;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getMipmap() {
        return mipmap;
    }

    public void setMipmap(int mipmap) {
        this.mipmap = mipmap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
