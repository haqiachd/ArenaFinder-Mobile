package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.AktivitasModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Digunakan untuk menerima response dari server pada data AktivitasModel di list AktivitasSecondAdapter
 *
 */
public class AktivitasSecondResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private ArrayList<AktivitasModel> data;

    public AktivitasSecondResponse(String status, String message, ArrayList<AktivitasModel> data) {
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

    public ArrayList<AktivitasModel> getData() {
        return data;
    }

    public void setData(ArrayList<AktivitasModel> data) {
        this.data = data;
    }
}
