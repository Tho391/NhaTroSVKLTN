package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("pass")
    var pass: String
) : Serializable