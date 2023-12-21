package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.CreateBookingModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menerima response dari server pada data CreateBooking
 *
 */
public class CreateBookingResponse {

    @Expose
    @SerializedName("status")
    private String  status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private CreateBookingModel data;

    public CreateBookingResponse(String status, String message, CreateBookingModel data) {
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

    public CreateBookingModel getData() {
        return data;
    }

    public void setData(CreateBookingModel data) {
        this.data = data;
    }
}
