package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyModel {

    @Expose
    @SuppressWarnings("otp")
    private String otp;

    @Expose
    @SuppressWarnings("start_millis")
    private long startMillis;

    @Expose
    @SuppressWarnings("end_millis")
    private long endMillis;

    @Expose
    @SuppressWarnings("type")
    private String type;

    @Expose
    @SuppressWarnings("device")
    private String device;

    @Expose
    @SuppressWarnings("resend")
    private int resend;

    @Expose
    @SerializedName("resend_millis")
    private long resendMillis;

    public VerifyModel(String otp, long startMillis, long endMillis, String type, String device, int resend, long resendMillis) {
        this.otp = otp;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.type = type;
        this.device = device;
        this.resend = resend;
        this.resendMillis = resendMillis;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(int startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(int endMillis) {
        this.endMillis = endMillis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getResend() {
        return resend;
    }

    public void setResend(int resend) {
        this.resend = resend;
    }

    public long getResendMillis() {
        return resendMillis;
    }

    public void setResendMillis(long resendMillis) {
        this.resendMillis = resendMillis;
    }
}
