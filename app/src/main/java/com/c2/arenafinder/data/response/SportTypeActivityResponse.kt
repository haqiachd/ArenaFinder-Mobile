package com.c2.arenafinder.data.response

import com.c2.arenafinder.data.model.SportTypeActivityModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Digunakan untuk menerima response dari server pada data sport type pada aktivitas
 *
 */
class SportTypeActivityResponse(
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("message")
    val message : String,
    @Expose
    @SerializedName("data")
    val data : SportTypeActivityModel
)