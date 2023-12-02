package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.NotificationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private ArrayList<NotificationModel> data;

    public NotificationResponse(String status, String message, ArrayList<NotificationModel> data) {
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

    public ArrayList<NotificationModel> getData() {
        return data;
    }

    public void setData(ArrayList<NotificationModel> data) {
        this.data = data;
    }
}
