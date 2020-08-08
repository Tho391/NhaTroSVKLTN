package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Image

data class ApartmentResponse(
    @SerializedName("idNhatro")
    @Expose
    val idNhatro: Int = 0,
    @SerializedName("Title")
    @Expose
    val title: String = "",
    @SerializedName("Tenchutro")
    @Expose
    val tenchutro: String = "",
    @SerializedName("Sdt")
    @Expose
    val sdt: String = "",
    @SerializedName("Diachi")
    @Expose
    val diachi: String = "",
    @SerializedName("idQuan")
    @Expose
    val idQuan: Int = 0,
    @SerializedName("idThanhpho")
    @Expose
    val idThanhPho: Int = 1,
    @SerializedName("LocalX")
    @Expose
    val localX: Double = 0.0,
    @SerializedName("LocalY")
    @Expose
    val localY: Double = 0.0,
    @SerializedName("date")
    @Expose
    val date: String = "",
    @SerializedName("Dientich")
    @Expose
    val dientich: Int = 0,
    @SerializedName("Phong")
    @Expose
    val phong: Int = 0,
    @SerializedName("Nhavesinh")
    @Expose
    val nhavesinh: Int = 0,
    @SerializedName("Mota")
    @Expose
    val mota: String = "",
    @SerializedName("Gia")
    @Expose
    val gia: Int = 0,
    @SerializedName("Maylanh")
    @Expose
    val maylanh: Int = 0,
    @SerializedName("Giuxe")
    @Expose
    val giuxe: Int = 0,
    @SerializedName("Nuocnong")
    @Expose
    val nuocnong: Int = 0,
    @SerializedName("Dien")
    @Expose
    val dien: Int = 0,
    @SerializedName("Nuoc")
    @Expose
    val nuoc: Int = 0,
    @SerializedName("Loainha")
    @Expose
    val loainha: String = "",
    @SerializedName("img")
    @Expose
    val img: String = "",
    @SerializedName("Vote")
    @Expose
    val vote: Float = 0F,
    @SerializedName("wifi")
    @Expose
    val wifi: Int = 0,
    @SerializedName("gio")
    @Expose
    val time: Int = 0,
    @SerializedName("chungchu")
    @Expose
    val key: Int = 0,


    @SerializedName("data") val data: String? = null,

    @SerializedName("url1") var url1: String? = null,
    @SerializedName("url2") var url2: String? = null,
    @SerializedName("url3") var url3: String? = null,
    @SerializedName("url4") var url4: String? = null,
    @SerializedName("url5") var url5: String? = null


) {
    fun toApartment(): Apartment {
        return Apartment(
            idNhatro,
            title,
            diachi,
            "",
            vote,
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
            null,
            idQuan
        )
    }
}