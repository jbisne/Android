package com.study.android.androidproject;

public class PharmDTO
{
    private double lng; //(경도)
    private double lat; //(위도)
    private String name;

    public double getLNG() {
        return lng;
    }

    public void setLNG(double lng) {
        this.lng = lng;
    }

    public double getLAT() {
        return lat;
    }

    public void setLAT(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
