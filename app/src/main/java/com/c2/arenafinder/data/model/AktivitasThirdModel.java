package com.c2.arenafinder.data.model;

public class AktivitasThirdModel {

    private String image;

    private String name;

    private String venue;

    private String jadwal;

    private String anggota;

    public AktivitasThirdModel(String aktivitasImage, String name, String venue, String jadwal, String anggota) {
        this.image = aktivitasImage;
        this.name = name;
        this.venue = venue;
        this.jadwal = jadwal;
        this.anggota = anggota;
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

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getAnggota() {
        return anggota;
    }

    public void setAnggota(String anggota) {
        this.anggota = anggota;
    }
}
