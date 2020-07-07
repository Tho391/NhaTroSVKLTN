package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.User
import java.io.Serializable

data class LoginResponse(
    @SerializedName("access_token")
    @Expose
    val token: String? = null,
    @SerializedName("idUser")
    @Expose
    val id: Int? = null,
    @SerializedName("Username")
    @Expose
    val username: String? = null,
    @SerializedName("Password")
    @Expose
    val password: String? = null,
    @SerializedName("Ho")
    @Expose
    val lastName: String? = null,
    @SerializedName("Ten")
    @Expose
    val firstName: String? = null,
    @SerializedName("NgaySinh")
    @Expose
    val dateOfBirth: String? = null,
    @SerializedName("Diachi")
    @Expose
    val address: String? = null,
    @SerializedName("TenQuan")
    @Expose
    val district: String? = null,
    @SerializedName("Tenthanhpho")
    @Expose
    val city: String? = null,
    @SerializedName("Sdt")
    @Expose
    val phoneNumber: String? = null,
    @SerializedName("photo")
    @Expose
    val photo: String? = null,
    @SerializedName("data")
    @Expose
    val data: String? = null
) : Serializable {
    fun toUser(): User {
        return User(
            id,
            firstName,
            dateOfBirth,
            address,
            district,
            city,
            phoneNumber,
            photo,
            username,
            password,
            lastName,
            token
        )
    }

    companion object {
        fun from(user: User): LoginResponse {
            return LoginResponse(
                user.getShortToken(),
                user.id,
                user.email,
                user.pass,
                user.lastName,
                user.firstName,
                user.dateOfBirth,
                user.address,
                user.district,
                user.city,
                user.phoneNumber,
                user.avatar
            )
        }
    }
}