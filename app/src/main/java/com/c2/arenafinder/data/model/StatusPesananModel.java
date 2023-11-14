package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusPesananModel {

    public static final String DI_PESAN = "Pending", DI_SETUJUI = "Accepted", DI_TOLAK = "Rejected";

    @Expose
    @SerializedName("id_booking")
    private int idBooking;

    @Expose
    @SerializedName("id_venue")
    private int idVenue;

    @Expose
    @SerializedName("total_price")
    private int totalPrice;

    @Expose
    @SerializedName("venue_name")
    private String venueName;

    @Expose
    @SerializedName("payment_method")
    private String paymentMethod;

    @Expose
    @SerializedName("payment_status")
    private String paymentStatus;

    @Expose
    @SerializedName("tanggal_pesan")
    private String tanggalPesan;

    @Expose
    @SerializedName("tanggal_konfirmasi")
    private String tanggalKonfirmasi;

    @Expose
    @SerializedName("total_jadwal")
    private int totalJadwal;

    @Expose
    @SerializedName("venue_photo")
    private String venuePhoto;

    public StatusPesananModel(int idPesanan, int idVenue, String venueName, int totalPrice, String paymentMethod, String paymentStatus, String tanggalPesan, String tanggalKonfirmasi, int totalJadwal, String venuePhoto) {
        this.idBooking = idPesanan;
        this.idVenue = idVenue;
        this.venueName = venueName;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.tanggalPesan = tanggalPesan;
        this.tanggalKonfirmasi = tanggalKonfirmasi;
        this.totalJadwal = totalJadwal;
        this.venuePhoto = venuePhoto;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public int getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(int idVenue) {
        this.idVenue = idVenue;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTanggalPesan() {
        return tanggalPesan;
    }

    public void setTanggalPesan(String tanggalPesan) {
        this.tanggalPesan = tanggalPesan;
    }

    public String getTanggalKonfirmasi() {
        return tanggalKonfirmasi;
    }

    public void setTanggalKonfirmasi(String tanggalKonfirmasi) {
        this.tanggalKonfirmasi = tanggalKonfirmasi;
    }

    public int getTotalJadwal() {
        return totalJadwal;
    }

    public void setTotalJadwal(int totalJadwal) {
        this.totalJadwal = totalJadwal;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenuePhoto() {
        return venuePhoto;
    }

    public void setVenuePhoto(String venuePhoto) {
        this.venuePhoto = venuePhoto;
    }
}
