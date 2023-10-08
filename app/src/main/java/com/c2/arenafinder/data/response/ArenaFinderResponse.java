package com.c2.arenafinder.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArenaFinderResponse {

    @Expose
    @SerializedName("status")
    public String status;
    @Expose
    @SerializedName("message")
    public String message;

    public ArenaFinderResponse(String status, String message) {
        this.status = status;
        this.message = message;
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
}
