package com.c2.arenafinder.data.model;

public class JadwalPickerModel {

    private int idJadwal;

    private String session;

    private int price;

    private boolean selected;

    public JadwalPickerModel(int idJadwal, String session, int price, boolean selected) {
        this.idJadwal = idJadwal;
        this.session = session;
        this.price = price;
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
