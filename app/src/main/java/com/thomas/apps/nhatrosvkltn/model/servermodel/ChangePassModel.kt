package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangePassModel(
    @SerializedName("Username") @Expose val email: String,
    @SerializedName("current_password") @Expose val oldPass: String,
    @SerializedName("new_password") @Expose val newPass: String
)