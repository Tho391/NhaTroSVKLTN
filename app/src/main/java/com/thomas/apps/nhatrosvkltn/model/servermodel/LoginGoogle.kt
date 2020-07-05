package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.SerializedName

data class LoginGoogle(
    @SerializedName("Ho") val lastName: String? = null,
    @SerializedName("Ten") val firstName: String? = null,
    @SerializedName("photourl") val photoUrl: String? = null,
    @SerializedName("gmail") val mail: String? = null,
    @SerializedName("id") val id: String? = null
)