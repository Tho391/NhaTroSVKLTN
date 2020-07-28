package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.Image

data class ImageResponse(
    @SerializedName("idHinhanh") val id: Int = 0,
    @SerializedName("idNhatro") val apartmentId: Int? = null,
    @SerializedName("Hinhanh") val photoUrl: String = ""
) {
    fun toImage(): Image = Image(id, photoUrl)
}