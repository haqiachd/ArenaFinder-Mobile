package com.c2.arenafinder.data.model;

public class VenueSecondModel {

    public String venueImage;

    public String venueName;

    public String venueSport;

    public float venueRatting;

    public String venueStatus;

    public String venueDescFirst;

    public String venueDescSecond;

    public VenueSecondModel(String venueImage, String venueName, String venueSport, float venueRatting, String venueStatus, String venueDescFirst, String venueDescSecond) {
        this.venueImage = venueImage;
        this.venueName = venueName;
        this.venueSport = venueSport;
        this.venueRatting = venueRatting;
        this.venueStatus = venueStatus;
        this.venueDescFirst = venueDescFirst;
        this.venueDescSecond = venueDescSecond;
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

    public String getVenueDescFirst() {
        return venueDescFirst;
    }

    public void setVenueDescFirst(String venueDescFirst) {
        this.venueDescFirst = venueDescFirst;
    }

    public String getVenueDescSecond() {
        return venueDescSecond;
    }

    public void setVenueDescSecond(String venueDescSecond){
        this.venueDescSecond = venueDescSecond;
    }

}
