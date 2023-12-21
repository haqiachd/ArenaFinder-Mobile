package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data dari jam operasional venue
 *
 */
public class JamOperasionalModel {

    @Expose
    @SerializedName("Senin")
    private DayOperasionalModel senin;

    @Expose
    @SerializedName("Selasa")
    private DayOperasionalModel selasa;

    @Expose
    @SerializedName("Rabu")
    private DayOperasionalModel rabu;

    @Expose
    @SerializedName("Kamis")
    private DayOperasionalModel kamis;

    @Expose
    @SerializedName("Jumat")
    private DayOperasionalModel jumat;

    @Expose
    @SerializedName("Sabtu")
    private DayOperasionalModel sabtu;

    @Expose
    @SerializedName("Minggu")
    private DayOperasionalModel minggu;

    public JamOperasionalModel(DayOperasionalModel senin, DayOperasionalModel selasa, DayOperasionalModel rabu, DayOperasionalModel kamis, DayOperasionalModel jumat, DayOperasionalModel sabtu, DayOperasionalModel minggu) {
        this.senin = senin;
        this.selasa = selasa;
        this.rabu = rabu;
        this.kamis = kamis;
        this.jumat = jumat;
        this.sabtu = sabtu;
        this.minggu = minggu;
    }

    public DayOperasionalModel getSenin() {
        return senin;
    }

    public void setSenin(DayOperasionalModel senin) {
        this.senin = senin;
    }

    public DayOperasionalModel getSelasa() {
        return selasa;
    }

    public void setSelasa(DayOperasionalModel selasa) {
        this.selasa = selasa;
    }

    public DayOperasionalModel getRabu() {
        return rabu;
    }

    public void setRabu(DayOperasionalModel rabu) {
        this.rabu = rabu;
    }

    public DayOperasionalModel getKamis() {
        return kamis;
    }

    public void setKamis(DayOperasionalModel kamis) {
        this.kamis = kamis;
    }

    public DayOperasionalModel getJumat() {
        return jumat;
    }

    public void setJumat(DayOperasionalModel jumat) {
        this.jumat = jumat;
    }

    public DayOperasionalModel getSabtu() {
        return sabtu;
    }

    public void setSabtu(DayOperasionalModel sabtu) {
        this.sabtu = sabtu;
    }

    public DayOperasionalModel getMinggu() {
        return minggu;
    }

    public void setMinggu(DayOperasionalModel minggu) {
        this.minggu = minggu;
    }
}
