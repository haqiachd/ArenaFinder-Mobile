package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data dari coordinate pada venue
 *
 */
public class VenueCoordinateModel {

    @Expose
    @SerializedName("id_venue")
    private String idVenue;

    @Expose
    @SerializedName("venue_name")
    private String venueName;

    @Expose
    @SerializedName("coordinate")
    private String coordinate;

    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("sport")
    private String sport;

    public VenueCoordinateModel(String idVenue, String venueName, String coordinate, String location, String sport) {
        this.idVenue = idVenue;
        this.venueName = venueName;
        this.coordinate = coordinate;
        this.location = location;
        this.sport = sport;
    }

    public String getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(String idVenue) {
        this.idVenue = idVenue;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
