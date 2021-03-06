package com.thomas.apps.nhatrosvkltn.model

import com.thomas.apps.nhatrosvkltn.model.servermodel.ApartmentResponse
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Apartment(
    val id: Int = 0,
    val title: String = "",
    val address: String = "",
    val district: String = "",
    val rating: Float = 0F,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val description: String = "",
    val ownerName: String = "",
    val phone: String = "",
    val createAt: String = "",
    val price: Int = 0,
    val electric: Int = 0,
    val water: Int = 0,
    val area: Int = 0,
    val wifi: Boolean = false,
    val time: Boolean = false,
    val key: Boolean = false,
    val parking: Boolean = false,
    val air: Boolean = false,
    val heater: Boolean = false,
    val images: List<Image>? = null,
    val user: User? = null,

    val districtId: Int = 0
) : Serializable {
    constructor(
        title: String,
        address: String,
        latitude: Double,
        longitude: Double,
        description: String,
        ownerName: String,
        phone: String,
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
        images: List<Image>? = null,
        user: User? = null,
        districtId: Int
    )
            : this(
        -1,
        title,
        address,
        "",
        0F,
        latitude,
        longitude,
        description,
        ownerName,
        phone,
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
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
        user,
        districtId
    )

    fun toApartmentResponse(): ApartmentResponse {
        return ApartmentResponse(
            idNhatro = id,
            title = title,
            tenchutro = ownerName,
            sdt = phone,
            diachi = address,
            idQuan = districtId,
            idThanhPho = 1,
            localX = latitude,
            localY = longitude,
            date = createAt,
            dientich = area,
            phong = 0,
            nhavesinh = 0,
            mota = description,
            gia = price,
            maylanh = if (air) 1 else 0,
            giuxe = if (parking) 1 else 0,
            nuocnong = if (heater) 1 else 0,
            dien = electric,
            nuoc = water,
            loainha = "",
            img = if (!images.isNullOrEmpty()) images[0].url else "",
            vote = rating,
            wifi = if (wifi) 1 else 0,
            time = if (time) 1 else 0,
            key = if (key) 1 else 0,
            data = null
        )
//        return ApartmentResponse(
//            id,
//            title,
//            ownerName,
//            phone,
//            address,
//            districtId,
//            idThanhPho = 1,
//            localX = latitude,
//            localY = longitude,
//            date = createAt,
//            dientich = 0,
//            phong = area,
//            nhavesinh = 0,
//            0,
//            description,
//            maylanh = price,
//            giuxe = if (air) 1 else 0,
//            nuocnong = if (parking) 1 else 0,
//            dien = if (heater) 1 else 0,
//            nuoc = electric,
//            water,
//            img = "",
//
//            if (images != null) images[0].url else null,
//            rating?.toDouble(),
//            time = if (wifi) 1 else 0,
//            key = if (time) 1 else 0,
//            if (key) 1 else 0,
//            districtId
//        )
    }
}