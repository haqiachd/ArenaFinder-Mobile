package com.c2.arenafinder.data.model;

import java.util.ArrayList;

public class VenueBookingModel {

    private int idLapangan;

    private String lapanganImg;

    private String lapanganName;

    private String totalSlot;

    private ArrayList<JadwalPickerModel> jadwal;

    public VenueBookingModel(int idLapangan, String lapanganImg, String lapanganName, String totalSlot, ArrayList<JadwalPickerModel> jadwal) {
        this.idLapangan = idLapangan;
        this.lapanganImg = lapanganImg;
        this.lapanganName = lapanganName;
        this.totalSlot = totalSlot;
        this.jadwal = jadwal;
    }

    public int getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(int idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getLapanganImg() {
        return lapanganImg;
    }

    public void setLapanganImg(String lapanganImg) {
        this.lapanganImg = lapanganImg;
    }

    public String getLapanganName() {
        return lapanganName;
    }

    public void setLapanganName(String lapanganName) {
        this.lapanganName = lapanganName;
    }

    public String getTotalSlot() {
        return totalSlot;
    }

    public void setTotalSlot(String totalSlot) {
        this.totalSlot = totalSlot;
    }

    public ArrayList<JadwalPickerModel> getJadwal() {
        return jadwal;
    }

    public void setJadwal(ArrayList<JadwalPickerModel> jadwal) {
        this.jadwal = jadwal;
    }
}
