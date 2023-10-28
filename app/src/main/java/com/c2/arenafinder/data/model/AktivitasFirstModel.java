package com.c2.arenafinder.data.model;

public class AktivitasFirstModel {

    private String aktivitasImage;

    private String aktivitasName;

    private String aktivitasVenue;

    private int aktivitasAnggota;

    private int aktivitasAnggotaMax;

    private String aktivitasTanggal;

    public AktivitasFirstModel(String aktivitasImage, String aktivitasName, String aktivitasVenue, int aktivitasAnggota, int aktivitasAnggotaMax, String aktivitasTanggal) {
        this.aktivitasImage = aktivitasImage;
        this.aktivitasName = aktivitasName;
        this.aktivitasVenue = aktivitasVenue;
        this.aktivitasAnggota = aktivitasAnggota;
        this.aktivitasAnggotaMax = aktivitasAnggotaMax;
        this.aktivitasTanggal = aktivitasTanggal;
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

    public int getAktivitasAnggota() {
        return aktivitasAnggota;
    }

    public void setAktivitasAnggota(int aktivitasAnggota) {
        this.aktivitasAnggota = aktivitasAnggota;
    }

    public int getAktivitasAnggotaMax() {
        return aktivitasAnggotaMax;
    }

    public void setAktivitasAnggotaMax(int aktivitasAnggotaMax) {
        this.aktivitasAnggotaMax = aktivitasAnggotaMax;
    }

    public String getAktivitasTanggal() {
        return aktivitasTanggal;
    }

    public void setAktivitasTanggal(String aktivitasTanggal) {
        this.aktivitasTanggal = aktivitasTanggal;
    }
}
