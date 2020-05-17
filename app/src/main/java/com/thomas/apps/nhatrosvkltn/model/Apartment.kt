package com.thomas.apps.nhatrosvkltn.model

data class Apartment(
    val id: Int,
    var title: String,
    var address: String,
    var district: String,
    var rating: Float,
    var latitude: Double,
    var longitude: Double,
    var description: String,
    var ownerName: String,
    var phone: String,
    var createAt: String,
    var price: Long,
    var electric: Long,
    var water: Long,
    var area: Int,
    var wifi: Boolean,
    var time: Boolean,
    var key: Boolean,
    var parking: Boolean,
    var air: Boolean,
    var heater: Boolean,

    var images: List<Image>,
    var user: User
)