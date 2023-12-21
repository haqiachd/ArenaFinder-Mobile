package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Digunakan untuk menyimpan data-data dari tipe olahraga pada aktivitas
 *
 */
class SportTypeActivityModel(
    @Expose
    @SerializedName("img_url")
    val imgUrl: String,
    @Expose
    @SerializedName("activities")
    val activities : ArrayList<AktivitasModel>
)