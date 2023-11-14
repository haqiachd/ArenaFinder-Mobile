package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.AktivitasMemberModel;
import com.c2.arenafinder.data.model.AktivitasModel;
import com.c2.arenafinder.data.model.VenueContactModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AktivitasDetailedResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private Data data;

    public AktivitasDetailedResponse(String status, String message, Data data) {
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
        @SerializedName("aktivitas_data")
        private AktivitasModel aktivitasData;

        @Expose
        @SerializedName("aktivitas_contact")
        private ArrayList<VenueContactModel> aktivitasContact;

        @Expose
        @SerializedName("aktivitas_member")
        private ArrayList<AktivitasMemberModel> aktivitasMember;

        @Expose
        @SerializedName("joined")
        private boolean joined;

        public Data(AktivitasModel aktivitasData, ArrayList<VenueContactModel> aktivitasContact, ArrayList<AktivitasMemberModel> aktivitasMember, boolean joined) {
            this.aktivitasData = aktivitasData;
            this.aktivitasContact = aktivitasContact;
            this.aktivitasMember = aktivitasMember;
            this.joined = joined;
        }

        public AktivitasModel getAktivitasData() {
            return aktivitasData;
        }

        public void setAktivitasData(AktivitasModel aktivitasData) {
            this.aktivitasData = aktivitasData;
        }

        public ArrayList<VenueContactModel> getAktivitasContact() {
            return aktivitasContact;
        }

        public void setAktivitasContact(ArrayList<VenueContactModel> aktivitasContact) {
            this.aktivitasContact = aktivitasContact;
        }

        public ArrayList<AktivitasMemberModel> getAktivitasMember() {
            return aktivitasMember;
        }

        public void setAktivitasMember(ArrayList<AktivitasMemberModel> aktivitasMember) {
            this.aktivitasMember = aktivitasMember;
        }

        public boolean isJoined() {
            return joined;
        }

        public void setJoined(boolean joined) {
            this.joined = joined;
        }
    }

}
