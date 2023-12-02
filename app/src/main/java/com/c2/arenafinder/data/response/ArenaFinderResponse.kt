package com.c2.arenafinder.data.response

import com.c2.arenafinder.data.model.ArenaFinderModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArenaFinderResponse(
    @Expose
    @SerializedName("status")
    var status : String?,
    @Expose
    @SerializedName("message")
    var message : String?,
    @Expose
    @SerializedName("data")
    var data : ArenaFinderModel
)
