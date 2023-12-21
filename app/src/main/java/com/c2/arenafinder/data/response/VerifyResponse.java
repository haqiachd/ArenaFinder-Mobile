package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.VerifyModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menerima response dari server pada data verifikasi email
 *
 */
public class VerifyResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private VerifyModel data;

    public VerifyResponse(String status, String message, VerifyModel data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VerifyModel getData() {
        return data;
    }

    public void setData(VerifyModel data) {
        this.data = data;
    }
}
