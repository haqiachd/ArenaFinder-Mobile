package com.c2.arenafinder.data.model;

public class HomeInfoModel {

    private String imgUrl;

    private String imgLink;

    public HomeInfoModel(String imgUrl, String imgLink) {
        this.imgUrl = imgUrl;
        this.imgLink = imgLink;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
