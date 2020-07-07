package com.thomas.apps.nhatrosvkltn.api

import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.ApartmentResponse
import com.thomas.apps.nhatrosvkltn.model.servermodel.CommentResponse
import com.thomas.apps.nhatrosvkltn.model.servermodel.LoginResponse
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository {
    companion object {
        val apiServices by lazy {
            ApiServices.create()
        }
    }

    fun getApartments(): Observable<List<ApartmentResponse>> {
        return apiServices.getApartments()
    }

    fun getApartment(apartmentId: Int): Observable<ApartmentResponse> {
        return apiServices.getApartment(apartmentId)
    }

    fun filterApartments(filterModel: FilterModel): Observable<List<ApartmentResponse>> {
        return apiServices.filterApartments(filterModel)
    }

    fun searchApartments(query: String): Observable<List<ApartmentResponse>> {
        return apiServices.searchApartments(query)
    }

    fun postApartment(token: String, apartment: ApartmentResponse): Observable<ApartmentResponse> {
        return apiServices.postApartment(token, apartment)
    }

    fun postApartment(
        token: String,
        fileName: RequestBody,
        file: MultipartBody.Part,
        apartment: ApartmentResponse
    ): Observable<ApartmentResponse> {
        return apiServices.postApartment(token, fileName, file, apartment)
    }

    fun getComments(apartmentId: Int): Observable<List<CommentResponse>> {
        return apiServices.getComments(apartmentId)
    }

    fun sendComment(token: String, comment: CommentResponse): Observable<CommentResponse> {
        return apiServices.sendComment(token, comment)
    }

    fun login(email: String, pass: String): Observable<LoginResponse> {
        return apiServices.login(LoginResponse(username = email, password = pass))
    }

    fun register(
        register: Register,
        fileName: RequestBody?,
        file: MultipartBody.Part?
    ): Observable<Register> {
        return apiServices.register(register, fileName, file)
    }

    fun editUser(register: Register): Observable<Register> = apiServices.editUser(register)
    fun changePass(
        token: String,
        email: String,
        oldPass: String,
        newPass: String
    ): Observable<Register> = apiServices.changePass(token, email, oldPass, newPass)

    fun loginWithGoogle(user: User) = apiServices.loginWithGoogle(user)
    fun getImages(apartmentId: Int) = apiServices.getImages(apartmentId)
    fun sendRating(token: String, apartmentId: Int, rating: Float) =
        apiServices.sendRating(token, apartmentId, rating)

    fun getUserApartment(token: String, userId: Int) = apiServices.getUserApartment(token, userId)
    fun editApartment(token: String, apartmentResponse: ApartmentResponse) =
        apiServices.editApartment(token, apartmentResponse)

}


