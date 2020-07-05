package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.SerializedName
import java.io.File

data class PostApartment(
    @SerializedName("Tenchutro")
    val ownerName: String?,
    @SerializedName("Sdt")
    val phone: String?,
    @SerializedName("Diachi")
    val address: String?,
    @SerializedName("idQuan")
    val district: Int = 0,
    @SerializedName("idThanhpho")
    val city: Int = 1,
    @SerializedName("LocalX")
    val latitude: Double?,
    @SerializedName("LocalY")
    val longitude: Double?,
    @SerializedName("Date")
    val createAt: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Dientich")
    val area: Int? = 0,
    @SerializedName("Phong")
    val room: Int? = 0,
    @SerializedName("Nhavesinh")
    val toilet: Int? = 0,
    @SerializedName("Mota")
    val description: String?,
    @SerializedName("Gia")
    val price: Int? = 0,
    @SerializedName("Maylanh")
    val air: Int? = 0,
    @SerializedName("Giuxe")
    val parking: Int? = 0,
    @SerializedName("Nuocnong")
    val heater: Int? = 0,
    @SerializedName("Dien")
    val electric: Int? = 0,
    @SerializedName("Nuoc")
    val water: Int? = 0,
    @SerializedName("Loainha")
    val type: String?,
    @SerializedName("wifi")
    val wifi: Int? = 0,
    @SerializedName("Gio")
    val time: Int? = 0,
    @SerializedName("chungchu")
    val key: Int? = 0,
    @SerializedName("file")
    val images: List<File>
)