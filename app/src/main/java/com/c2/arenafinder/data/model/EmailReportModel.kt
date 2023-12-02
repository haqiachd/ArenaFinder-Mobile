package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailReportModel(
    @Expose
    @SerializedName("email")
    val email : String?,
    @Expose
    @SerializedName("username")
    val username : String,
    @Expose
    @SerializedName("username_reported")
    val usernameReported : String,
    @Expose
    @SerializedName("venue_id")
    val venueId : String?,
    @Expose
    @SerializedName("venue_name")
    val venueName : String?,
    @Expose
    @SerializedName("reason")
    val reason : String,
    @Expose
    @SerializedName("comment")
    val comment : String
)
