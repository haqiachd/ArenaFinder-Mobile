package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data dari photo venue yang akan ditampilkan pada detail venue
 *
 */
public class VenuePhotos {

    @Expose
    @SerializedName("photo")
    private String photo;

    public VenuePhotos(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
