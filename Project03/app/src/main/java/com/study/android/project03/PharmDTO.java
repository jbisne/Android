package com.study.android.project03;

public class PharmDTO
{
    private Double lng; //(경도)
    private Double lat; //(위도)
    private String name;

    public Double getLNG() {
        return lng;
    }

    public void setLNG(Double lng) {
        this.lng = lng;
    }

    public Double getLAT() {
        return lat;
    }

    public void setLAT(Double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
