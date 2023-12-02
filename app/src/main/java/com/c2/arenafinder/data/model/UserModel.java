package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @Expose
    @SerializedName("id_users")
    private int idUser;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("full_name")
    private String nama;
    @Expose
    @SerializedName("no_hp")
    private String noHp;
    @Expose
    @SerializedName("alamat")
    private String alamat;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("level")
    private String level;
    @Expose
    @SerializedName("is_verified")
    private String isVerified;
    @Expose
    @SerializedName("user_photo")
    private String userPhoto;
    @Expose
    @SerializedName("created_at")
    private String createdAt;

    public UserModel(int idUser, String username, String email, String nama, String noHp, String alamat, String password, String level, String isVerified, String userPhoto, String createdAt) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.password = password;
        this.level = level;
        this.isVerified = isVerified;
        this.userPhoto = userPhoto;
        this.createdAt = createdAt;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVerified() {
        return isVerified;
    }

    public void setVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

}
