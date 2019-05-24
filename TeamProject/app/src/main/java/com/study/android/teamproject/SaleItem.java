package com.study.android.teamproject;

public class SaleItem
{
    private String name;
    private String age;
    private int resId;

    public SaleItem(String name, String age, int resId)
    {
        this.name = name;
        this.age = age;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
