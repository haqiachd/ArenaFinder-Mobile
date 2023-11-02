package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueFirstModel {

    @Expose
    @SerializedName("id_venue")
    private int idVenue;

    @Expose
    @SerializedName("venue_photo")
    public String venueImage;

    @Expose
    @SerializedName("venue_name")
    public String venueName;

    @Expose
    @SerializedName("olahraga")
    public String venueSport;

    @Expose
    @SerializedName("rating")
    public float venueRatting;

    @Expose
    @SerializedName("status")
    public String venueStatus;

    @Expose
    @SerializedName("")
    public String venueDesc;

    public VenueFirstModel(int idVenue, String venueImage, String venueName, String venueSport, float venueRatting, String venueStatus, String venueDesc) {
        this.idVenue = idVenue;
        this.venueImage = venueImage;
        this.venueName = venueName;
        this.venueSport = venueSport;
        this.venueRatting = venueRatting;
        this.venueStatus = venueStatus;
        this.venueDesc = venueDesc;
    }

    public int getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(int idVenue) {
        this.idVenue = idVenue;
    }

    public String getVenueImage() {
        return venueImage;
    }

    public void setVenueImage(String venueImage) {
        this.venueImage = venueImage;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueSport() {
        return venueSport;
    }

    public void setVenueSport(String venueSport) {
        this.venueSport = venueSport;
    }

    public float getVenueRatting() {
        return venueRatting;
    }

    public void setVenueRatting(float venueRatting) {
        this.venueRatting = venueRatting;
    }

    public String getVenueStatus() {
        return venueStatus;
    }

    public void setVenueStatus(String venueStatus) {
        this.venueStatus = venueStatus;
    }

    public String getVenueDesc() {
        return venueDesc;
    }

    public void setVenueDesc(String venueDesc) {
        this.venueDesc = venueDesc;
    }
}
