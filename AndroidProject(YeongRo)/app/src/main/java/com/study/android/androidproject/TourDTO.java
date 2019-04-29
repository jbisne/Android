package com.study.android.androidproject;

public class TourDTO {

    private String ctName; // 지역명
    private String sigungu; //시군구
    private String addr1; //주소
    private String addr2; //주소2
    private String title; //축제명
    private String overview; // 개요
    private String tel; // 전화번호
    private String image; // 관광지 이미지
    private String readcount; // 조회수
    private String contentTypeId;
    private String contentId;
    private String mapx; //GPS X좌표
    private String mapy; //GPS Y좌표

    public TourDTO(String title, String mapx, String mapy) {
        this.title = title;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public TourDTO(String title, String addr1, String addr2, String mapx, String mapy, String overview, String image){
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.mapx = mapx;
        this.mapy = mapy;
        this.overview = overview;
        this.image = image;
    }
    public TourDTO(String title, String addr1, String image, String contentTypeId, String contentId){
        this.title = title;
        this.addr1 = addr1;
        this.image = image;
        this.contentTypeId = contentTypeId;
        this.contentId = contentId;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getSigungu() {
        return sigungu;
    }

    public void setSigungu(String sigungu) {
        this.sigungu = sigungu;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReadcount() {
        return readcount;
    }

    public void setReadcount(String readcount) {
        this.readcount = readcount;
    }

    public String getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
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
}
