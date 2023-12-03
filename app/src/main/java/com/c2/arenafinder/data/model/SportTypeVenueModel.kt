package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SportTypeVenueModel(
    @Expose
    @SerializedName("img_url")
    val imgUrl: String,
    @Expose
    @SerializedName("venues")
    val venues : ArrayList<VenueExtendedModel>
)