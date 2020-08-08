package com.thomas.apps.nhatrosvkltn.model.imgbb


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("extension") var extension: String,
    @SerializedName("filename") var filename: String,
    @SerializedName("mime") var mime: String,
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
)