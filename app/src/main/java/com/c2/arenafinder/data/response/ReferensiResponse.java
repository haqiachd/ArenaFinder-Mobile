package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.model.VenueCoordinateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Digunakan untuk menerima response dari server pada data ReferensiModel di halaman referensi
 *
 */
public class ReferensiResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private Data data;

    public ReferensiResponse(String status, String message, Data data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        @Expose
        @SerializedName("top_ratting")
        private ArrayList<ReferensiModel> topRatting;

        @Expose
        @SerializedName("venue_lokasi")
        private ArrayList<ReferensiModel> venueLokasi;

        @Expose
        @SerializedName("venue_kosong")
        private ArrayList<ReferensiModel> venueKosong;

        @Expose
        @SerializedName("venue_gratis")
        private ArrayList<ReferensiModel> venueGratis;

        @Expose
        @SerializedName("venue_berbayar")
        private ArrayList<ReferensiModel> venueBerbayar;

        @Expose
        @SerializedName("venue_disewakan")
        private ArrayList<ReferensiModel> venueDisewakan;

        @Expose
        @SerializedName("coordinate")
        private ArrayList<VenueCoordinateModel> coordinate;

        public Data(ArrayList<ReferensiModel> topRatting, ArrayList<ReferensiModel> venueLokasi, ArrayList<ReferensiModel> venueKosong, ArrayList<ReferensiModel> venueGratis, ArrayList<ReferensiModel> venueBerbayar, ArrayList<ReferensiModel> venueDisewakan, ArrayList<VenueCoordinateModel> coordinate) {
            this.topRatting = topRatting;
            this.venueLokasi = venueLokasi;
            this.venueKosong = venueKosong;
            this.venueGratis = venueGratis;
            this.venueBerbayar = venueBerbayar;
            this.venueDisewakan = venueDisewakan;
            this.coordinate = coordinate;
        }

        public ArrayList<ReferensiModel> getTopRatting() {
            return topRatting;
        }

        public void setTopRatting(ArrayList<ReferensiModel> topRatting) {
            this.topRatting = topRatting;
        }

        public ArrayList<ReferensiModel> getVenueLokasi() {
            return venueLokasi;
        }

        public void setVenueLokasi(ArrayList<ReferensiModel> venueLokasi) {
            this.venueLokasi = venueLokasi;
        }

        public ArrayList<ReferensiModel> getVenueKosong() {
            return venueKosong;
        }

        public void setVenueKosong(ArrayList<ReferensiModel> venueKosong) {
            this.venueKosong = venueKosong;
        }

        public ArrayList<ReferensiModel> getVenueGratis() {
            return venueGratis;
        }

        public void setVenueGratis(ArrayList<ReferensiModel> venueGratis) {
            this.venueGratis = venueGratis;
        }

        public ArrayList<ReferensiModel> getVenueBerbayar() {
            return venueBerbayar;
        }

        public void setVenueBerbayar(ArrayList<ReferensiModel> venueBerbayar) {
            this.venueBerbayar = venueBerbayar;
        }

        public ArrayList<ReferensiModel> getVenueDisewakan() {
            return venueDisewakan;
        }

        public void setVenueDisewakan(ArrayList<ReferensiModel> venueDisewakan) {
            this.venueDisewakan = venueDisewakan;
        }

        public ArrayList<VenueCoordinateModel> getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(ArrayList<VenueCoordinateModel> coordinate) {
            this.coordinate = coordinate;
        }
    }

}
