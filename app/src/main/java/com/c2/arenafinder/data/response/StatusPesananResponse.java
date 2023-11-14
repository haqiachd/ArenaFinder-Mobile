package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.StatusPesananModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatusPesananResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private ArrayList<StatusPesananModel> data;

    public StatusPesananResponse(String status, String message, ArrayList<StatusPesananModel> data) {
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

    public ArrayList<StatusPesananModel> getData() {
        return data;
    }

    public void setData(ArrayList<StatusPesananModel> data) {
        this.data = data;
    }
}
