package com.c2.arenafinder.data.model;

public class AktivitasMemberModel {

    private String photo;

    private String fullName;

    private String username;

    private String joinData;

    public AktivitasMemberModel(String photo, String name, String username, String joinData) {
        this.photo = photo;
        fullName = name;
        this.username = username;
        this.joinData = joinData;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinData() {
        return joinData;
    }

    public void setJoinData(String joinData) {
        this.joinData = joinData;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
