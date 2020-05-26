package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String
)