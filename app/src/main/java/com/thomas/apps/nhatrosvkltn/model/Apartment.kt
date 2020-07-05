package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.servermodel.ApartmentResponse
import java.io.Serializable
import java.util.*

data class Apartment(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("district")
    val district: String?,
    @SerializedName("rating")
    val rating: Float? = 0F,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("ownerName")
    val ownerName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("created_at")
    val createAt: String?,
    @SerializedName("price")
    val price: Int? = 0,
    @SerializedName("electric")
    val electric: Int? = 0,
    @SerializedName("water")
    val water: Int? = 0,
    @SerializedName("area")
    val area: Int? = 0,
    @SerializedName("wifi")
    val wifi: Boolean = false,
    @SerializedName("time")
    val time: Boolean = false,
    @SerializedName("key")
    val key: Boolean = false,
    @SerializedName("parking")
    val parking: Boolean = false,
    @SerializedName("air")
    val air: Boolean = false,
    @SerializedName("heater")
    val heater: Boolean = false,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("user")
    val user: User?
) : Serializable {
    constructor(
        title: String,
        address: String?,
        district: String?,
        latitude: Double,
        longitude: Double,
        description: String?,
        ownerName: String?,
        phone: String?,
        price: Long,
        electric: Int,
        water: Int,
        area: Int,
        wifi: Boolean,
        time: Boolean,
        key: Boolean,
        parking: Boolean,
        air: Boolean,
        heater: Boolean,
        images: List<Image>,
        user: User
    )
            : this(
        -1,
        title,
        address,
        district,
        0F,
        latitude,
        longitude,
        description,
        ownerName,
        phone,
        Date().toString(),
        price.toInt(),
        electric,
        water,
        area,
        wifi,
        time,
        key,
        parking,
        air,
        heater,
        images,
        user
    )

    fun toApartmentResponse(): ApartmentResponse {
        return ApartmentResponse(
            id,
            title,
            ownerName,
            phone,
            address,
            district,
            null,
            latitude,
            longitude,
            createAt,
            0,
            area,
            0,
            0,
            description,
            price,
            if (air) 1 else 0,
            if (parking) 1 else 0,
            if (heater) 1 else 0,
            electric,
            water,
            null,
            images[0].url,
            rating?.toDouble(),
            if (wifi) 1 else 0,
            if (time) 1 else 0,
            if (key) 1 else 0
        )
    }
}