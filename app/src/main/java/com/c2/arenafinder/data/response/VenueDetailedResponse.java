package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.FasilitasModel;
import com.c2.arenafinder.data.model.JamOperasionalModel;
import com.c2.arenafinder.data.model.VenueCommentModel;
import com.c2.arenafinder.data.model.VenueContactModel;
import com.c2.arenafinder.data.model.VenueDetailedModel;
import com.c2.arenafinder.data.model.VenuePhotos;
import com.c2.arenafinder.data.model.VenueRatingModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Digunakan untuk menerima response dari server pada data di halaman venue detail
 *
 */
public class VenueDetailedResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private Data data;

    public VenueDetailedResponse(String status, String message, Data data) {
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


    public static class Data {

        @Expose
        @SerializedName("venue_data")
        private VenueDetailedModel venue;

        @Expose
        @SerializedName("operasional")
        private JamOperasionalModel jamOperasional;

        @Expose
        @SerializedName("contact")
        private ArrayList<VenueContactModel> contact;

        @Expose
        @SerializedName("fasilitas")
        private ArrayList<FasilitasModel> fasilitas;

        @Expose
        @SerializedName("rating")
        private VenueRatingModel rating;

        @Expose
        @SerializedName("comment")
        private ArrayList<VenueCommentModel> comment;

        @Expose
        @SerializedName("photos")
        private ArrayList<VenuePhotos> photo;
        public Data(VenueDetailedModel venue, JamOperasionalModel jamOperasional, ArrayList<VenueContactModel> contact, ArrayList<FasilitasModel> fasilitas, VenueRatingModel rating, ArrayList<VenueCommentModel> comment, ArrayList<VenuePhotos> photo) {
            this.venue = venue;
            this.jamOperasional = jamOperasional;
            this.contact = contact;
            this.fasilitas = fasilitas;
            this.rating = rating;
            this.comment = comment;
            this.photo = photo;
        }

        public VenueDetailedModel getVenue() {
            return venue;
        }

        public void setVenue(VenueDetailedModel venue) {
            this.venue = venue;
        }

        public JamOperasionalModel getJamOperasional() {
            return jamOperasional;
        }

        public void setJamOperasional(JamOperasionalModel jamOperasional) {
            this.jamOperasional = jamOperasional;
        }

        public ArrayList<VenueContactModel> getContact() {
            return contact;
        }

        public void setContact(ArrayList<VenueContactModel> contact) {
            this.contact = contact;
        }

        public ArrayList<FasilitasModel> getFasilitas() {
            return fasilitas;
        }

        public void setFasilitas(ArrayList<FasilitasModel> fasilitas) {
            this.fasilitas = fasilitas;
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

        public ArrayList<VenuePhotos> getPhoto() {
            return photo;
        }

        public void setPhoto(ArrayList<VenuePhotos> photo) {
            this.photo = photo;
        }
    }

}
