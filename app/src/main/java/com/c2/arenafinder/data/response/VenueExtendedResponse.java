package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.VenueExtendedModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VenueExtendedResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private ArrayList<VenueExtendedModel> data;

    public VenueExtendedResponse(String status, String message, ArrayList<VenueExtendedModel> data) {
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

    public ArrayList<VenueExtendedModel> getData() {
        return data;
    }

    public void setData(ArrayList<VenueExtendedModel> data) {
        this.data = data;
    }
}
