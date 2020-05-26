package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("avatar")
    var avatar: String
)