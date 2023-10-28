package com.c2.arenafinder.data.model;

public class AktivitasSecondModel {

    private String image;

    private String name;

    private String venue;

    private String tanggal;

    private String time;

    private int anggota;

    private int anggotaMax;

    private int price;

    public AktivitasSecondModel(String aktivitasImage, String name, String venue, int anggota, int anggotaMax, String tanggal, String time, int price) {
        this.image = aktivitasImage;
        this.name = name;
        this.venue = venue;
        this.tanggal = tanggal;
        this.time = time;
        this.anggota = anggota;
        this.anggotaMax = anggotaMax;
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getAnggota() {
        return anggota;
    }

    public int getAnggotaMax(){
        return anggotaMax;
    }

    public void setAnggota(int anggota) {
        this.anggota = anggota;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
