package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.VenueFirstModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VenueResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private ArrayList<VenueFirstModel> data;

    public VenueResponse(String status, String message, ArrayList<VenueFirstModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<VenueFirstModel> getData() {
        return data;
    }

    public void setData(ArrayList<VenueFirstModel> data) {
        this.data = data;
    }
}
