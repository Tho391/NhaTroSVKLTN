package com.thomas.apps.nhatrosvkltn.model

import java.io.Serializable

data class User(
    val id: Int? = null,
    var firstName: String? = "",
    val dateOfBirth: String? = "",
    val address: String? = "",
    val district: String? = "",
    val city: String? = "",
    val phoneNumber: String? = "",
    val avatar: String? = null,
    val email: String? = "",
    val pass: String? = "",
    val lastName: String? = "",
    val token: String? = ""
) : Serializable {
    fun getName(): String {
        return "$lastName $firstName"
    }
}