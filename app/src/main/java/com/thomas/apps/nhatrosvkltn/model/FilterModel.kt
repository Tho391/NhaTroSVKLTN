package com.thomas.apps.nhatrosvkltn.model

data class FilterModel(
    var address: String? = null,
    var district: String? = null,
    var rating: Float? = null,
    var priceStart: Int? = null,
    var priceEnd: Int? = null,
    var areaStart: Int? = null,
    var areaEnd: Int? = null,
    var wifi: Boolean? = null,
    var time: Boolean? = null,
    var key: Boolean? = null,
    var car: Boolean? = null,
    var air: Boolean? = null,
    var heater: Boolean? = null
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    private fun resetFilter() {

    }
}