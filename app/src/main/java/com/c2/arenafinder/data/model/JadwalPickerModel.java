package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalPickerModel {

    @Expose
    @SerializedName("id_price")
    private int idJadwal;

    @Expose
    @SerializedName("session")
    private String session;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("is_booked")
    private boolean isBooked;

    @Expose
    @SerializedName("selected")
    private boolean selected;

    public JadwalPickerModel(int idJadwal, String session, int price, boolean isBooked, boolean selected) {
        this.idJadwal = idJadwal;
        this.session = session;
        this.price = price;
        this.isBooked = isBooked;
        this.selected = selected;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getSession() {
        return session;
    }

    public void setSexssion(String session) {
        this.session = session;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
