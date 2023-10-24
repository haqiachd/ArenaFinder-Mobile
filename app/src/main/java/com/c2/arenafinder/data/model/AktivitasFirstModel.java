package com.c2.arenafinder.data.model;

public class AktivitasFirstModel {

    private String aktivitasImage;

    private String aktivitasName;

    private String aktivitasVenue;

    public AktivitasFirstModel(String aktivitasImage, String aktivitasName, String aktivitasVenue) {
        this.aktivitasImage = aktivitasImage;
        this.aktivitasName = aktivitasName;
        this.aktivitasVenue = aktivitasVenue;
    }


    public String getAktivitasImage() {
        return aktivitasImage;
    }

    public void setAktivitasImage(String aktivitasImage) {
        this.aktivitasImage = aktivitasImage;
    }

    public String getAktivitasName() {
        return aktivitasName;
    }

    public void setAktivitasName(String aktivitasName) {
        this.aktivitasName = aktivitasName;
    }

    public String getAktivitasVenue() {
        return aktivitasVenue;
    }

    public void setAktivitasVenue(String aktivitasVenue) {
        this.aktivitasVenue = aktivitasVenue;
    }
}
