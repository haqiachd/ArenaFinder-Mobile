package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SportTypeActivityModel(
    @Expose
    @SerializedName("img_url")
    val imgUrl: String,
    @Expose
    @SerializedName("activities")
    val activities : ArrayList<AktivitasModel>
)