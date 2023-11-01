package com.c2.arenafinder.data.model;

public class VenueReviewModel {

    private int idReview;

    private String photoProfile;

    private String username;

    private String fullName;

    private int ratting;

    private String comment;

    private String date;

    public VenueReviewModel(int idReview, String  photoProfile, String username, String fullName, int ratting, String comment, String date) {
        this.idReview = idReview;
        this.username = username;
        this.fullName = fullName;
        this.photoProfile = photoProfile;
        this.ratting = ratting;
        this.comment = comment;
        this.date = date;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }
}
