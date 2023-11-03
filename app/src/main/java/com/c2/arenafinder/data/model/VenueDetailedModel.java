package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueDetailedModel {

    @Expose
    @SerializedName("id_venue")
    private int idVenue;

    @Expose
    @SerializedName("venue_name")
    private String venueName;

    @Expose
    @SerializedName("shared")
    private int shared;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("coordinate")
    private String coordinate;

    @Expose
    @SerializedName("harga_sewa")
    private int hargaSewa;

    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("desc_facility")
    private String descFacility;

    @Expose
    @SerializedName("desc_venue")
    private String descVenue;

    @Expose
    @SerializedName("sport")
    private String sport;

    @Expose
    @SerializedName("views")
    private int views;

    @Expose
    @SerializedName("status")
    private String status;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String value) {
        this.venueName = value;
    }

    public int getShared() {
        return shared;
    }

    public void setShared(int value) {
        this.shared = value;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String value) {
        this.coordinate = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public int getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(int value) {
        this.hargaSewa = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public String getDescFacility() {
        return descFacility;
    }

    public void setDescFacility(String value) {
        this.descFacility = value;
    }

    public String getDescVenue() {
        return descVenue;
    }

    public void setDescVenue(String value) {
        this.descVenue = value;
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

    public int getViews() {
        return views;
    }

    public void setViews(int value) {
        this.views = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}
