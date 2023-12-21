package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data dari venue yang dibooking oleh user, seperti id_venue, id_booking, tanggal dan lain-lain
 *
 */
public class CreateBookingModel {

    @Expose
    @SerializedName("id_venue")
    private int idVenue;

    @Expose
    @SerializedName("id_booking")
    private int idBooking;

    @Expose
    @SerializedName("id_price")
    private int idPrice;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("total_price")
    private int totalPrice;

    public CreateBookingModel(int idVenue, int idBooking, int idPrice, String email, String date, int totalPrice) {
        this.idVenue = idVenue;
        this.idBooking = idBooking;
        this.idPrice = idPrice;
        this.email = email;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public int getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(int idVenue) {
        this.idVenue = idVenue;
    }

    public int getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(int idPrice) {
        this.idPrice = idPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }
}
