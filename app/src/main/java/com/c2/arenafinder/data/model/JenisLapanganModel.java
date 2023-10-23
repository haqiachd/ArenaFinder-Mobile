package com.c2.arenafinder.data.model;

import androidx.annotation.DrawableRes;

public class JenisLapanganModel {

    private @DrawableRes int iconDrawable;

    private String namaLapangan;

    public JenisLapanganModel(@DrawableRes int iconDrawable, String namaLapangan) {
        this.iconDrawable = iconDrawable;
        this.namaLapangan = namaLapangan;
    }

    @DrawableRes
    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(@DrawableRes int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }
}
