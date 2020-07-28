package com.thomas.apps.nhatrosvkltn.model

import java.io.Serializable

data class User(
    val id: Int = 0,
    var firstName: String = "",
    val dateOfBirth: String = "",
    val address: String = "",
    val districtId: Int = 1,
    val cityId: Int = 1,
    val phoneNumber: String = "",
    val avatar: String = "",
    val email: String = "",
    val pass: String = "",
    val lastName: String = "",
    private val token: String = ""
) : Serializable {
    fun getName(): String = "$lastName $firstName"

    fun getToken(): String = "bearer $token"

    fun hasToken(): Boolean = token.isNotEmpty()
    fun getShortToken(): String = token
}