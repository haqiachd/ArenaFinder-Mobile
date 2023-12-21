package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data venue pada list venue
 *
 */
public class VenueExtendedModel {

    @Expose
    @SerializedName("id_venue")
    private int idVenue;
    @Expose
    @SerializedName("venue_name")
    private String venueName;
    @Expose
    @SerializedName("venue_photo")
    private String venuePhoto;
    @Expose
    @SerializedName("jam_buka")
    private String jamBuka;
    @Expose
    @SerializedName("jam_tutup")
    private String jamTutup;
    @Expose
    @SerializedName("coordinate")
    private String coordinate;
    @Expose
    @SerializedName("harga")
    private int harga;
    @Expose
    @SerializedName("total_review")
    private int totalReview;
    @Expose
    @SerializedName("rating")
    private float rating;
    @Expose
    @SerializedName("harga_sewa")
    private int hargaSewa;
    @Expose
    @SerializedName("sport")
    private String sport;

    public VenueExtendedModel(int idVenue, String venueName, String venuePhoto, String jamBuka, String jamTutup, String coordinate, int harga, int totalReview, float rating, int hargaSewa, String sport, String status) {
        this.idVenue = idVenue;
        this.venueName = venueName;
        this.venuePhoto = venuePhoto;
        this.jamBuka = jamBuka;
        this.jamTutup = jamTutup;
        this.coordinate = coordinate;
        this.harga = harga;
        this.totalReview = totalReview;
        this.rating = rating;
        this.hargaSewa = hargaSewa;
        this.sport = sport;
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String value) {
        this.venueName = value;
    }

    public String getVenuePhoto() {
        return venuePhoto;
    }

    public void setVenuePhoto(String value) {
        this.venuePhoto = value;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String value) {
        this.jamBuka = value;
    }

    public String getJamTutup() {
        return jamTutup;
    }

    public void setJamTutup(String value) {
        this.jamTutup = value;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String value) {
        this.coordinate = value;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int value) {
        this.harga = value;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int value) {
        this.totalReview = value;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float value) {
        this.rating = value;
    }

    public int getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(int value) {
        this.hargaSewa = value;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String value) {
        this.sport = value;
    }

    public int getidVenue() {
        return idVenue;
    }

    public void setidVenue(int value) {
        this.idVenue = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}
