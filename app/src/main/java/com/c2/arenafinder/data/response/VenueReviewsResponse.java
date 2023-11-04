package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.model.VenueRatingModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VenueReviewsResponse {

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private Data data;

    public VenueReviewsResponse(String status, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        @Expose
        @SerializedName("venue_review")
        private VenueRatingModel rating;

        @Expose
        @SerializedName("venue_comment")
        private ArrayList<VenueCommentModel> comment;

        public Data(VenueRatingModel rating, ArrayList<VenueCommentModel> comment) {
            this.rating = rating;
            this.comment = comment;
        }

        public VenueRatingModel getRating() {
            return rating;
        }

        public void setRating(VenueRatingModel rating) {
            this.rating = rating;
        }

        public ArrayList<VenueCommentModel> getComment() {
            return comment;
        }

        public void setComment(ArrayList<VenueCommentModel> comment) {
            this.comment = comment;
        }
    }
}
