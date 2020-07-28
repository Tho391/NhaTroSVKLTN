package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName

data class FilterModel(
    @SerializedName("adrress") var address: String? = "",
    @SerializedName("quan") var district: Int = 0,
    @SerializedName("rating") var rating: Float = 5F,
    @SerializedName("minprice") var priceStart: Int = 0,
    @SerializedName("maxprice") var priceEnd: Int = 10000000,
    @SerializedName("minarea") var areaStart: Int = 0,
    @SerializedName("maxarea") var areaEnd: Int = 100,
    @SerializedName("wifi") var wifi: Int? = null,
    @SerializedName("gio") var time: Int? = null,
    @SerializedName("chungchu") var key: Int? = null,
    @SerializedName("giuxe") var car: Int? = null,
    @SerializedName("maylanh") var air: Int? = null,
    @SerializedName("nuocnong") var heater: Int? = null
) {
    private fun resetFilter() {

    }

    fun isEmpty() =
        district != 0 &&
                rating != 0F &&
                priceStart != 0 &&
                priceEnd != 0 &&
                areaStart != 0 &&
                areaEnd != 0 &&
                wifi != 0 &&
                time != 0 &&
                key != 0 &&
                car != 0 &&
                air != 0 &&
                heater != 0

}