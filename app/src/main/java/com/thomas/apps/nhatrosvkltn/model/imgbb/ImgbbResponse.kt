package com.thomas.apps.nhatrosvkltn.model.imgbb


import com.google.gson.annotations.SerializedName

data class ImgbbResponse(
    @SerializedName("data") var `data`: Data,
    @SerializedName("status") var status: Int,
    @SerializedName("success") var success: Boolean
)