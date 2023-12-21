package com.c2.arenafinder.data.response;

import com.c2.arenafinder.data.model.AktivitasModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Digunakan untuk menerima response dari server pada data AktivitasModel di halaman aktivitas
 *
 */
public class AktivitasResponse {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private Data data;

    public AktivitasResponse(String status, String message, Data data) {
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
        @SerializedName("aktivitas_baru")
        private ArrayList<AktivitasModel> aktivitasBaru;

        @Expose
        @SerializedName("aktivitas_kosong")
        private ArrayList<AktivitasModel> aktivitasKosong;

        @Expose
        @SerializedName("aktivitas_semua")
        private ArrayList<AktivitasModel> semuaAktivitas;

        public Data(ArrayList<AktivitasModel> aktivitasBaru, ArrayList<AktivitasModel> aktivitasKosong, ArrayList<AktivitasModel> semuaAktivitas) {
            this.aktivitasBaru = aktivitasBaru;
            this.aktivitasKosong = aktivitasKosong;
            this.semuaAktivitas = semuaAktivitas;
        }

        public ArrayList<AktivitasModel> getAktivitasBaru() {
            return aktivitasBaru;
        }

        public void setAktivitasBaru(ArrayList<AktivitasModel> aktivitasBaru) {
            this.aktivitasBaru = aktivitasBaru;
        }

        public ArrayList<AktivitasModel> getAktivitasKosong() {
            return aktivitasKosong;
        }

        public void setAktivitasKosong(ArrayList<AktivitasModel> aktivitasKosong) {
            this.aktivitasKosong = aktivitasKosong;
        }

        public ArrayList<AktivitasModel> getSemuaAktivitas() {
            return semuaAktivitas;
        }

        public void setSemuaAktivitas(ArrayList<AktivitasModel> semuaAktivitas) {
            this.semuaAktivitas = semuaAktivitas;
        }
    }

}
