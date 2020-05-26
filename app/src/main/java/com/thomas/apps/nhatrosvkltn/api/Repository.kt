package com.thomas.apps.nhatrosvkltn.api

import com.thomas.apps.nhatrosvkltn.model.Apartment
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class Repository {
    companion object {
        val apiServices by lazy {
            ApiServices.create()
        }
        var disposable: Disposable? = null
    }

    fun getApartments(): Observable<List<Apartment>> {
        return apiServices.getApartments()
    }
}