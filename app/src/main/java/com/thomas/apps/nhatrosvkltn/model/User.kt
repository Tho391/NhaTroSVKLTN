package com.thomas.apps.nhatrosvkltn.model

import java.io.Serializable

data class User(
    var id: Int? = 0,
    var firstName: String? = "",
    var dateOfBirth: String? = "",
    var address: String? = "",
    var districtId: Int? = 1,
    var cityId: Int? = 1,
    var phoneNumber: String? = "",
    var avatar: String? = "",
    var email: String? = "",
    var pass: String? = "",
    var lastName: String? = "",
    private val token: String? = ""
) : Serializable {
    fun getName(): String = "$lastName $firstName"

    fun getToken(): String = "bearer $token"

    fun hasToken(): Boolean = !token.isNullOrEmpty()
    fun getShortToken(): String = token ?: ""
}