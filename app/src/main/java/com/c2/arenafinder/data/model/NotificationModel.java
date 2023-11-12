package com.c2.arenafinder.data.model;

public class NotificationModel {

    private int idNotif;

    private String title;

    private String body;

    private String type;

    private String date;

    public NotificationModel(int idNotif, String title, String body, String type, String date) {
        this.idNotif = idNotif;
        this.title = title;
        this.body = body;
        this.type = type;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdNotif() {
        return idNotif;
    }

    public void setIdNotif(int idNotif) {
        this.idNotif = idNotif;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
