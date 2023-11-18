package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.ReferensiModel;
import com.c2.arenafinder.data.model.VenueCoordinateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private HomeResponse.Data data;

    public HomeResponse(String status, String message, HomeResponse.Data data) {
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

    public HomeResponse.Data getData() {
        return data;
    }

    public void setData(HomeResponse.Data data) {
        this.data = data;
    }

    public static class Data {

        @Expose
        @SerializedName("venue_baru")
        private ArrayList<ReferensiModel> venueBaru;

        @Expose
        @SerializedName("rekomendasi")
        private ArrayList<ReferensiModel> venueRekomendasi;

        @Expose
        @SerializedName("aktivitas")
        private ArrayList<AktivitasModel> aktivitasSeru;

        @Expose
        @SerializedName("coordinate")
        private ArrayList<VenueCoordinateModel> coordinate;

        @Expose
        @SerializedName("dekat_kamu")
        private ArrayList<ReferensiModel> venueLokasi;

        public Data(ArrayList<ReferensiModel> venueBaru, ArrayList<ReferensiModel> venueRekomendasi, ArrayList<AktivitasModel> aktivitasSeru, ArrayList<VenueCoordinateModel> coordinate, ArrayList<ReferensiModel> venueLokasi) {
            this.venueBaru = venueBaru;
            this.venueRekomendasi = venueRekomendasi;
            this.aktivitasSeru = aktivitasSeru;
            this.coordinate = coordinate;
            this.venueLokasi = venueLokasi;
        }


        public ArrayList<ReferensiModel> getVenueBaru() {
            return venueBaru;
        }

        public void setVenueBaru(ArrayList<ReferensiModel> venueBaru) {
            this.venueBaru = venueBaru;
        }

        public ArrayList<ReferensiModel> getVenueRekomendasi() {
            return venueRekomendasi;
        }

        public void setVenueRekomendasi(ArrayList<ReferensiModel> venueRekomendasi) {
            this.venueRekomendasi = venueRekomendasi;
        }

        public ArrayList<AktivitasModel> getAktivitasSeru() {
            return aktivitasSeru;
        }

        public void setAktivitasSeru(ArrayList<AktivitasModel> aktivitasSeru) {
            this.aktivitasSeru = aktivitasSeru;
        }

        public ArrayList<ReferensiModel> getVenueLokasi() {
            return venueLokasi;
        }

        public void setVenueLokasi(ArrayList<ReferensiModel> venueLokasi) {
            this.venueLokasi = venueLokasi;
        }

        public ArrayList<VenueCoordinateModel> getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(ArrayList<VenueCoordinateModel> coordinate) {
            this.coordinate = coordinate;
        }
    }

}
