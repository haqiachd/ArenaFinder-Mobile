package com.c2.arenafinder.data.model;

import com.google.gson.annotations.SerializedName;

public class EditCommentModel {

    @SerializedName("id_venue")
    private String idVenue;

    @SerializedName("id_users")
    private String idUsers;

    @SerializedName("star")
    private String star;

    @SerializedName("comment")
    private String comment;

    public EditCommentModel(String idVenue, String idUsers){
        this.idVenue = idVenue;
        this.idUsers = idUsers;
    }

    public EditCommentModel(String idVenue, String idUsers, String star, String comment) {
        this.idVenue = idVenue;
        this.idUsers = idUsers;
        this.star = star;
        this.comment = comment;
    }

    public String getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(String idVenue) {
        this.idVenue = idVenue;
    }

    public String getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(String idUsers) {
        this.idUsers = idUsers;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
