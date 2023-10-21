package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyModel {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("otp")
    private String otp;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("start_millis")
    private long startMillis;

    @Expose
    @SerializedName("end_millis")
    private long endMillis;

    @Expose
    @SerializedName("device")
    private String device;

    @Expose
    @SerializedName("resend")
    private int resend;

    @Expose
    @SerializedName("created_at")
    private String created;

    public VerifyModel(String email, String otp, String type, long  startMillis, long  endMillis, String device, int resend, String created) {
        this.email = email;
        this.otp = otp;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.type = type;
        this.device = device;
        this.resend = resend;
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
