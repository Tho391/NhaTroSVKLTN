package com.thomas.apps.nhatrosvkltn.api

import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class RepositoryTest {
    companion object {
        val apiServices by lazy {
            ApiServicesTest.create()
        }
        var disposable: Disposable? = null
    }

    fun getApartments(): Observable<List<Apartment>> {
        return apiServices.getApartments()
    }

    fun getApartment(apartmentId: Int): Observable<Apartment> {
        return apiServices.getApartment(apartmentId)
    }

    fun filterApartments(filterModel: FilterModel): Observable<List<Apartment>> {
        return apiServices.filterApartments(filterModel)
    }

    fun searchApartments(query: String): Observable<List<Apartment>> {
        return apiServices.searchApartments(query)
    }

    fun postApartment(apartment: Apartment): Observable<Apartment> {
        return apiServices.postApartment(apartment)
    }

    fun getComments(apartmentId: Int): Observable<List<Comment>> {
        return apiServices.getComments()
    }

    fun sendComment(comment: Comment): Observable<Comment> {
        return apiServices.sendComment(comment)
    }
}