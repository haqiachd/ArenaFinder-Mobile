package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueContactModel {

    @Expose
    @SerializedName("id_users")
    private int idUser;

    @Expose
    @SerializedName("user_photo")
    private String imgProfile;

    @Expose
    @SerializedName("full_name")
    private String fullName;

    @Expose
    @SerializedName("no_hp")
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
