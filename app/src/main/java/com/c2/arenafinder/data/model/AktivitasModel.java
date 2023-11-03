package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AktivitasModel {

    @Expose
    @SerializedName("id_aktvitias")
    private int idAktvitias;

    @Expose
    @SerializedName("venue_name")
    private String venueName;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("nama_aktivitas")
    private String namaAktivitas;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("max_member")
    private int maxMember;

    @Expose
    @SerializedName("start_hour")
    private String startHour;

    @Expose
    @SerializedName("photo")
    private String photo;

    @Expose
    @SerializedName("end_hour")
    private String endHour;

    @Expose
    @SerializedName("jumlah_member")
    private int jumlahMember;

    public AktivitasModel(int idAktvitias, String venueName, String date, String namaAktivitas, int price, int maxMember, String startHour, String photo, String endHour, int jumlahMember) {
        this.idAktvitias = idAktvitias;
        this.venueName = venueName;
        this.date = date;
        this.namaAktivitas = namaAktivitas;
        this.price = price;
        this.maxMember = maxMember;
        this.startHour = startHour;
        this.photo = photo;
        this.endHour = endHour;
        this.jumlahMember = jumlahMember;
    }

    public int getidAktvitias() {
        return idAktvitias;
    }

    public void setidAktvitias(int value) {
        this.idAktvitias = value;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String value) {
        this.venueName = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getNamaAktivitas() {
        return namaAktivitas;
    }

    public void setNamaAktivitas(String value) {
        this.namaAktivitas = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int value) {
        this.maxMember = value;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String value) {
        this.startHour = value;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String value) {
        this.photo = value;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String value) {
        this.endHour = value;
    }

    public int getJumlahMember() {
        return jumlahMember;
    }

    public void setJumlahMember(int value) {
        this.jumlahMember = value;
    }
}
