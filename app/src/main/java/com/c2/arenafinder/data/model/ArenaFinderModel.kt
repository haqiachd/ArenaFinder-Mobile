package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArenaFinderModel(
    @Expose
    @SerializedName("server_status")
    val serverStatus : Boolean,
    @Expose
    @SerializedName("have_update")
    val haveUpdate : Boolean,
    @Expose
    @SerializedName("min_version_code")
    val minVersionCode : Int,
    @Expose
    @SerializedName("new_version_name")
    val newVersionName : String,
    @Expose
    @SerializedName("update_link")
    val updateLink : String,
    @Expose
    @SerializedName("desc_update")
    val descUpdate : String,
)
