package com.thomas.apps.nhatrosvkltn.api


import com.google.gson.JsonElement
import com.thomas.apps.nhatrosvkltn.api.Status.*
import io.reactivex.annotations.NonNull


data class ApiResponse(
    val status: Status? = null,
    val data: JsonElement? = null,
    val error: Throwable? = null
) {

    fun loading(): ApiResponse? {
        return ApiResponse(LOADING, null, null)
    }

    fun success(@NonNull data: JsonElement?): ApiResponse? {
        return ApiResponse(SUCCESS, data, null)
    }

    fun error(@NonNull error: Throwable?): ApiResponse? {
        return ApiResponse(ERROR, null, error)
    }
}