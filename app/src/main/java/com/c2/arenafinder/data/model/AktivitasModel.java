package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AktivitasModel {

    public static final String STATUS_ONGOING = "ongoing", STATUS_FINISHED = "finished";

    @Expose
    @SerializedName("id_aktivitas")
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
    @SerializedName("desc_aktivitas")
    private String descAktivitas;

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
    @SerializedName("sport")
    private String jenisOlahraga;

    @Expose
    @SerializedName("end_hour")
    private String endHour;

    @Expose
    @SerializedName("jam_main")
    private String jamMain;

    @Expose
    @SerializedName("jumlah_member")
    private int jumlahMember;

    @Expose
    @SerializedName("membership")
    private String membership;

    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("coordinate")
    private String coordinate;

    public AktivitasModel(int idAktvitias, String venueName, String date, String namaAktivitas, String descAktivitas, int price, int maxMember, String jenisOlahraga, String jamMain, String startHour, String photo, String endHour, int jumlahMember, String membership, String location, String coordinate) {
        this.idAktvitias = idAktvitias;
        this.venueName = venueName;
        this.date = date;
        this.namaAktivitas = namaAktivitas;
        this.descAktivitas = descAktivitas;
        this.price = price;
        this.maxMember = maxMember;
        this.jenisOlahraga = jenisOlahraga;
        this.startHour = startHour;
        this.photo = photo;
        this.endHour = endHour;
        this.jumlahMember = jumlahMember;
        this.membership = membership;
        this.jamMain = jamMain;
        this.location = location;
        this.coordinate = coordinate;
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

    public String getJamMain() {
        return jamMain;
    }

    public void setJamMain(String jamMain) {
        this.jamMain = jamMain;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getJenisOlahraga() {
        return jenisOlahraga;
    }

    public void setJenisOlahraga(String jenisOlahraga) {
        this.jenisOlahraga = jenisOlahraga;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getDescAktivitas() {
        return descAktivitas;
    }

    public void setDescAktivitas(String descAktivitas) {
        this.descAktivitas = descAktivitas;
    }
}
