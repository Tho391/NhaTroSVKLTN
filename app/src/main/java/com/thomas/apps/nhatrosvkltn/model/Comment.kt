package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("user")
    var user: User,

    @SerializedName("apartment")
    var apartment: Apartment
)