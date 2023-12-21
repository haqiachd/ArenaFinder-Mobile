package com.c2.arenafinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Digunakan untuk menyimpan data-data dari member yang bergabung pada aktivitas tertentu
 *
 */
public class AktivitasMemberModel {

    @Expose
    @SerializedName("id_aktivitas")
    private String idAktivitas;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("user_photo")
    private String photo;

    @Expose
    @SerializedName("full_name")
    private String fullName;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("joined")
    private String joinDate;

    public AktivitasMemberModel(String idAktivitas, String email, String photo, String name, String username, String joinData) {
        this.idAktivitas = idAktivitas;
        this.email = email;
        this.photo = photo;
        fullName = name;
        this.username = username;
        this.joinDate = joinData;
    }

    public AktivitasMemberModel(String idAktivitas, String email){
        this.idAktivitas = idAktivitas;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdAktivitas() {
        return idAktivitas;
    }

    public void setIdAktivitas(String idAktivitas) {
        this.idAktivitas = idAktivitas;
    }
}
