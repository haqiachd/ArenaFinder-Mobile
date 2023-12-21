package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Digunakan untuk menyimpan data-data list dari lapangan venue saat booking
 *
 */
data class ListLapanganModel(
    @Expose
    @SerializedName("id_lapangan")
    val idLapangan : Int?
)