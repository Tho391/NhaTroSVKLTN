package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("url")
    val url: String?
) : Serializable