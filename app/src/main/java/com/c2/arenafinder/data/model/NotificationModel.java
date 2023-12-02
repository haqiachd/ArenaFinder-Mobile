package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @Expose
    @SerializedName("tanggal")
    private String tanggal;

    @Expose
    @SerializedName("tanggal_konfirmasi")
    private String tanggalKonfirmasi;

    @Expose
    @SerializedName("status")
    public String status;

    public NotificationModel(String tanggal, String tanggalKonfirmasi, String status) {
        this.tanggal = tanggal;
        this.tanggalKonfirmasi = tanggalKonfirmasi;
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalKonfirmasi() {
        return tanggalKonfirmasi;
    }

    public void setTanggalKonfirmasi(String tanggalKonfirmasi) {
        this.tanggalKonfirmasi = tanggalKonfirmasi;
    }
}
