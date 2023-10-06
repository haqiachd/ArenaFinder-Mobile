package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;

public class VerifyModel {

    @Expose
    @SuppressWarnings("otp")
    private String otp;

    @Expose
    @SuppressWarnings("start_millis")
    private int startMillis;

    @Expose
    @SuppressWarnings("end_millis")
    private int endMillis;

    @Expose
    @SuppressWarnings("type")
    private String type;

    @Expose
    @SuppressWarnings("device")
    private String device;

    public VerifyModel(String otp, int startMillis, int endMillis) {
        this.otp = otp;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(int startMillis) {
        this.startMillis = startMillis;
    }

    public int getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(int endMillis) {
        this.endMillis = endMillis;
    }

}
