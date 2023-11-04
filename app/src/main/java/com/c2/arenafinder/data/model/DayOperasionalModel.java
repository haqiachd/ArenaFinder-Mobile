package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayOperasionalModel {

    @Expose
    @SerializedName("opened")
    private String opened;

    @Expose
    @SerializedName("closed")
    private String closed;

    public DayOperasionalModel(String opened, String closed) {
        this.opened = opened;
        this.closed = closed;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
}
