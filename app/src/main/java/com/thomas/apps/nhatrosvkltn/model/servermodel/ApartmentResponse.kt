package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Image

data class ApartmentResponse(
    @SerializedName("idNhatro")
    @Expose
    val idNhatro: Int?,
    @SerializedName("Title")
    @Expose
    val title: String?,
    @SerializedName("Tenchutro")
    @Expose
    val tenchutro: String?,
    @SerializedName("Sdt")
    @Expose
    val sdt: String?,
    @SerializedName("Diachi")
    @Expose
    val diachi: String?,
    @SerializedName("Tenquan")
    @Expose
    val tenquan: String?,
    @SerializedName("Tenthanhpho")
    @Expose
    val tenthanhpho: String?,
    @SerializedName("LocalX")
    @Expose
    val localX: Double?,
    @SerializedName("LocalY")
    @Expose
    val localY: Double?,
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("Sate")
    @Expose
    val sate: Int?,
    @SerializedName("Dientich")
    @Expose
    val dientich: Int?,
    @SerializedName("Phong")
    @Expose
    val phong: Int?,
    @SerializedName("Nhavesinh")
    @Expose
    val nhavesinh: Int?,
    @SerializedName("Mota")
    @Expose
    val mota: String?,
    @SerializedName("Gia")
    @Expose
    val gia: Int?,
    @SerializedName("Maylanh")
    @Expose
    val maylanh: Int?,
    @SerializedName("Giuxe")
    @Expose
    val giuxe: Int?,
    @SerializedName("Nuocnong")
    @Expose
    val nuocnong: Int?,
    @SerializedName("Dien")
    @Expose
    val dien: Int?,
    @SerializedName("Nuoc")
    @Expose
    val nuoc: Int?,
    @SerializedName("Loainha")
    @Expose
    val loainha: String?,
    @SerializedName("img")
    @Expose
    val img: String?,
    @SerializedName("vote")
    @Expose
    val vote: Double?,
    @SerializedName("wifi")
    @Expose
    val wifi: Int?,
    @SerializedName("gio")
    @Expose
    val time: Int?,
    @SerializedName("chungchu")
    @Expose
    val key: Int?
) {
    fun toApartment(): Apartment {
        return Apartment(
            idNhatro,
            title,
            diachi,
            tenquan,
            vote?.toFloat(),
            localX,
            localY,
            mota,
            tenchutro,
            sdt,
            date,
            gia,
            dien,
            nuoc,
            dientich,
            wifi != 0,
            time != 0,
            key != 0,
            giuxe != 0,
            maylanh != 0,
            nuocnong != 0,
            listOf(Image(1, img)),
            null
        )
    }
}