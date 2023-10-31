package com.c2.arenafinder.data.model;

public class DatePickerModel {

    private String date;

    private String dayName;

    private String dateMonth;

    private boolean clicked;

    public DatePickerModel(String date, String dayName, String dateMonth, boolean clicked) {
        this.date = date;
        this.dayName = dayName;
        this.dateMonth = dateMonth;
        this.clicked = clicked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
