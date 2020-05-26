package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName

data class Apartment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("district")
    var district: String,
    @SerializedName("rating")
    var rating: Float,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("description")
    var description: String,
    @SerializedName("ownerName")
    var ownerName: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("createAt")
    var createAt: String,
    @SerializedName("price")
    var price: Long,
    @SerializedName("electric")
    var electric: Long,
    @SerializedName("water")
    var water: Long,
    @SerializedName("area")
    var area: Int,
    @SerializedName("wifi")
    var wifi: Boolean,
    @SerializedName("time")
    var time: Boolean,
    @SerializedName("key")
    var key: Boolean,
    @SerializedName("parking")
    var parking: Boolean,
    @SerializedName("air")
    var air: Boolean,
    @SerializedName("heater")
    var heater: Boolean,
    @SerializedName("images")
    var images: List<Image>,
    @SerializedName("user")
    var user: User
)