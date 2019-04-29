package com.study.android.androidproject;

import android.os.Parcel;
import android.os.Parcelable;

// 영로설명 : intetnt는 ArrayList를 사용할수가없어서
// Parcelable만들어서 보내줘야한다.

public class TourDTOParcelable implements Parcelable {

    private String title;
    private String addr1;
    private String mapx;
    private String mapy;


    public TourDTOParcelable(String title, String addr1,String mapx, String mapy){
        this.title = title;
        this.addr1 = addr1;
        this.mapx = mapx;
        this.mapy = mapy;
    }
    protected TourDTOParcelable(Parcel in) {

        this.title = in.readString();
        this.addr1 = in.readString();
        this.mapx = in.readString();
        this.mapy = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.addr1);
        dest.writeString(this.mapx);
        dest.writeString(this.mapy);
    }

    public static final Creator<TourDTOParcelable> CREATOR = new Creator<TourDTOParcelable>() {
        @Override
        public TourDTOParcelable createFromParcel(Parcel in) {
            return new TourDTOParcelable(in);
        }

        @Override
        public TourDTOParcelable[] newArray(int size) {
            return new TourDTOParcelable[size];
        }
    };


}
