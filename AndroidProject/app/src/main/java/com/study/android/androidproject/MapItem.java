package com.study.android.androidproject;

public class MapItem
{
    private String OBJECTID;    // 고유번호
    private String SHUNT_NAM;   // 대피소명
    private String ADR_NAM;     // 소재지(주소)
    private String TEL_NO_CN;   // 전화번호
    private double LAT;         // 경도
    private double LNG;         // 위도

    public MapItem(String OBJECTID, String SHUNT_NAM, String ADR_NAM, String TEL_NO_CN,
                   double LAT, double LNG)
    {
        this.OBJECTID = OBJECTID;
        this.SHUNT_NAM = SHUNT_NAM;
        this.ADR_NAM = ADR_NAM;
        this.TEL_NO_CN = TEL_NO_CN;
        this.LAT = LAT;
        this.LNG = LNG;
    }

    public String getOBJECTID() {
        return OBJECTID;
    }

    public void setOBJECTID(String OBJECTID) {
        this.OBJECTID = OBJECTID;
    }

    public String getSHUNT_NAM() {
        return SHUNT_NAM;
    }

    public void setSHUNT_NAM(String SHUNT_NAM) {
        this.SHUNT_NAM = SHUNT_NAM;
    }

    public String getADR_NAM() {
        return ADR_NAM;
    }

    public void setADR_NAM(String ADR_NAM) {
        this.ADR_NAM = ADR_NAM;
    }

    public String getTEL_NO_CN() {
        return TEL_NO_CN;
    }

    public void setTEL_NO_CN(String TEL_NO_CN) {
        this.TEL_NO_CN = TEL_NO_CN;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }
}
