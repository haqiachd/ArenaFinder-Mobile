package com.c2.arenafinder.data.model;

public class VenueContactModel {

    private int idUser;

    private String imgProfile;

    private String fullName;

    private String noHp;

    public VenueContactModel(int idUser, String imgProfile, String name, String noHp) {
        this.idUser = idUser;
        this.imgProfile = imgProfile;
        fullName = name;
        this.noHp = noHp;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}
