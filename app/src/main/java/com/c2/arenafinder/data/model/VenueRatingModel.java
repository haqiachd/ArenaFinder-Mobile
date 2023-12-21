package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data jumlah ulasan pada setiap rating
 *
 */
public class VenueRatingModel {

    @Expose
    @SerializedName("rating")
    private String rating;

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
    @SerializedName("total_review")
    private String totalReview;


    public VenueRatingModel(String rating, String rating1, String rating2, String rating3, String rating4, String rating5, String totalReview) {
        this.rating = rating;
        this.rating1 = rating1;
        this.rating2 = rating2;
        this.rating3 = rating3;
        this.rating4 = rating4;
        this.rating5 = rating5;
        this.totalReview = totalReview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating1() {
        return rating1;
    }

    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }

    public String getRating3() {
        return rating3;
    }

    public void setRating3(String rating3) {
        this.rating3 = rating3;
    }

    public String getRating4() {
        return rating4;
    }

    public void setRating4(String rating4) {
        this.rating4 = rating4;
    }

    public String getRating5() {
        return rating5;
    }

    public void setRating5(String rating5) {
        this.rating5 = rating5;
    }

    public String getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(String totalReview) {
        this.totalReview = totalReview;
    }
}
