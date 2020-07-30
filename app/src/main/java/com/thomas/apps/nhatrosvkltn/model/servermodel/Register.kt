package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.User

data class Register(
    @SerializedName("Ten") @Expose val firstName: String?,
    @SerializedName("Ho") @Expose val lastName: String? = null,
    @SerializedName("NgaySinh") @Expose val dateOfBirth: String? = null,
    @SerializedName("DiaChi") @Expose val address: String? = null,
    @SerializedName("idQuan") @Expose val districtId: Int? = 0,
    @SerializedName("ThanhPho") @Expose val city: Int = 1,
    @SerializedName("Sdt") @Expose val phone: String? = null,
    @SerializedName("email") @Expose val email: String? = null,
    @SerializedName("pass") @Expose val pass: String? = null,
    @SerializedName("data") @Expose val data: String? = null
) {
    companion object {
        fun fromUser(user: User) = Register(
            firstName = user.firstName,
            lastName = user.lastName,
            dateOfBirth = user.dateOfBirth,
            address = user.address,
            districtId = user.districtId,
            city = 1,
            phone = user.phoneNumber,
            email = user.email,
            pass = user.pass,
            data = null
        )
    }
}