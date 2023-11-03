package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueRatingModel {

    @Expose
    @SerializedName("rating_1")
    private String rating1;

    @Expose
    @SerializedName("rating_2")
    private String rating2;

    @Expose
    @SerializedName("rating_3")
    private String rating3;

    @Expose
    @SerializedName("rating_4")
    private String rating4;

    @Expose
    @SerializedName("rating_5")
    private String rating5;

    @Expose
    @SerializedName("total_Review")
    private String totalReview;


    public String getRating1() {
        return rating1;
    }

    public void setRating1(String value) {
        this.rating1 = value;
    }

    public String getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(String value) {
        this.totalReview = value;
    }

    public String getRating5() {
        return rating5;
    }

    public void setRating5(String value) {
        this.rating5 = value;
    }

    public String getRating4() {
        return rating4;
    }

    public void setRating4(String value) {
        this.rating4 = value;
    }

    public String getRating3() {
        return rating3;
    }

    public void setRating3(String value) {
        this.rating3 = value;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String value) {
        this.rating2 = value;
    }
}
