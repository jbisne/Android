package com.study.android.hw01;

public class SingerItem
{
    private String name;
    private String telnum;
    private int resId;
    private String gender;

    public SingerItem(String name, String telnum, int resId, String gender)
    {
        this.name = name;
        this.telnum = telnum;
        this.resId = resId;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

