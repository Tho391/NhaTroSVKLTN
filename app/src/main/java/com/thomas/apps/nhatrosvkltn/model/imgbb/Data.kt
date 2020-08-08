package com.thomas.apps.nhatrosvkltn.model.imgbb


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("delete_url") var deleteUrl: String,
    @SerializedName("display_url") var displayUrl: String,
    @SerializedName("expiration") var expiration: String,
    @SerializedName("id") var id: String,
    @SerializedName("image") var image: Image,
    @SerializedName("medium") var medium: Medium,
    @SerializedName("size") var size: Int,
    @SerializedName("thumb") var thumb: Thumb,
    @SerializedName("time") var time: String,
    @SerializedName("title") var title: String,
    @SerializedName("url") var url: String,
    @SerializedName("url_viewer") var urlViewer: String
)