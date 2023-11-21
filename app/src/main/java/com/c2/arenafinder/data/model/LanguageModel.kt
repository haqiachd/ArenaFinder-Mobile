package com.c2.arenafinder.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LanguageModel(
    @Expose
    @SerializedName("id")
    val indonesian : String?,
    @Expose
    @SerializedName("jv")
    val javanese : String?,
    @Expose
    @SerializedName("en")
    val english : String?
)
