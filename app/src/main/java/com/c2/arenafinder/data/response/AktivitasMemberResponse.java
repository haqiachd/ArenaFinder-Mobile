package com.c2.arenafinder.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AktivitasMemberResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private AktivitasMemberResponse data;

    public AktivitasMemberResponse(String status, String message, AktivitasMemberResponse data) {
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

    public AktivitasMemberResponse getData() {
        return data;
    }

    public void setData(AktivitasMemberResponse data) {
        this.data = data;
    }
}
