package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.User
import java.io.Serializable

data class LoginResponse(
    @SerializedName("access_token")
    @Expose
    val token: String = "",
    @SerializedName("idUser")
    @Expose
    val id: Int = 0,
    @SerializedName("Username")
    @Expose
    val username: String = "",
    @SerializedName("Password")
    @Expose
    val password: String = "",
    @SerializedName("Ho")
    @Expose
    val lastName: String = "",
    @SerializedName("Ten")
    @Expose
    val firstName: String = "",
    @SerializedName("NgaySinh")
    @Expose
    val dateOfBirth: String = "",
    @SerializedName("Diachi")
    @Expose
    val address: String = "",
    @SerializedName("idQuan")
    @Expose
    val districtId: Int = 0,
    @SerializedName("idThanhpho")
    @Expose
    val cityId: Int = 1,
    @SerializedName("Sdt")
    @Expose
    val phoneNumber: String = "",
    @SerializedName("photo")
    @Expose
    val photo: String = "",
    @SerializedName("data")
    @Expose
    val data: String? = null
) : Serializable {
    fun toUser(): User {
        return User(
            id = id,
            firstName = firstName,
            dateOfBirth = dateOfBirth,
            address = address,
            districtId = districtId,
            cityId = cityId,
            phoneNumber = phoneNumber,
            avatar = photo,
            email = username,
            pass = password,
            lastName = lastName,
            token = token
        )
    }

    companion object {
        fun from(user: User): LoginResponse {
            return LoginResponse(
                token = user.getShortToken(),
                id = user.id ?: 0,
                username = user.email ?: "",
                password = user.pass ?: "",
                lastName = user.lastName ?: "",
                firstName = user.firstName ?: "",
                dateOfBirth = user.dateOfBirth ?: "",
                address = user.address ?: "",
                districtId = user.districtId ?: 1,
                cityId = user.cityId ?: 1,
                phoneNumber = user.phoneNumber ?: "",
                photo = user.avatar ?: "",
                data = null
            )
        }
    }
}