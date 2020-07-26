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
    @SerializedName("wifi") var wifi: Int = 1,
    @SerializedName("gio") var time: Int = 1,
    @SerializedName("chungchu") var key: Int = 1,
    @SerializedName("giuxe") var car: Int = 1,
    @SerializedName("maylanh") var air: Int = 1,
    @SerializedName("nuocnong") var heater: Int = 1
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