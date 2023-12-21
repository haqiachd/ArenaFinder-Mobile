package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data fasilitas dari venue
 *
 */
public class FasilitasModel {

    @Expose
    @SerializedName("id_fasilitas")
    private String idFasilitas;

    @Expose
    @SerializedName("nama_fasilitas")
    private String namaFasilitas;

    @Expose
    @SerializedName("fasilitas_photo")
    private String fasilitasPhoto;

    public FasilitasModel(String idFasilitas, String namaFasilitas, String fasilitasPhoto) {
        this.idFasilitas = idFasilitas;
        this.namaFasilitas = namaFasilitas;
        this.fasilitasPhoto = fasilitasPhoto;
    }

    public String getIdFasilitas() {
        return idFasilitas;
    }

    public void setIdFasilitas(String idFasilitas) {
        this.idFasilitas = idFasilitas;
    }

    public String getNamaFasilitas() {
        return namaFasilitas;
    }

    public void setNamaFasilitas(String namaFasilitas) {
        this.namaFasilitas = namaFasilitas;
    }

    public String getFasilitasPhoto() {
        return fasilitasPhoto;
    }

    public void setFasilitasPhoto(String fasilitasPhoto) {
        this.fasilitasPhoto = fasilitasPhoto;
    }
}
