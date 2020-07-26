package com.thomas.apps.nhatrosvkltn.api

import com.google.gson.Gson
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.*
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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

    fun searchApartments(query: String) = apiServices.searchApartments(query)


    fun postApartment(token: String, apartment: ApartmentResponse): Observable<ApartmentResponse> {
        return apiServices.postApartment(token, apartment)
    }

    fun postApartment(
        token: String,
        fileName: RequestBody,
        file: MultipartBody.Part,
        apartment: ApartmentResponse
    ): Observable<ApartmentResponse> {
        return apiServices.postApartment(
            token,
            fileName,
            file,
            apartment.tenchutro!!,
            apartment.sdt!!,
            apartment.diachi!!,
            apartment.idQuan,
            1,
            apartment.localX!!,
            apartment.localY!!,
            apartment.date!!,
            apartment.title!!, apartment.dientich!!,
            apartment.phong!!,
            apartment.nhavesinh!!,
            apartment.mota!!,
            apartment.gia!!,
            apartment.nuocnong!!,
            apartment.dien!!,
            apartment.nuoc!!,
            apartment.loainha!!,
            apartment.wifi!!,
            apartment.time!!,
            apartment.key!!
        )
    }

    fun getComments(apartmentId: Int): Observable<List<CommentResponse>> {
        return apiServices.getComments(apartmentId)
    }

    fun sendComment(token: String, comment: CommentResponse) =
        apiServices.sendComment(token, comment.idNhatro, comment)


    fun login(email: String, pass: String): Observable<LoginResponse> {
        return apiServices.login(LoginResponse(username = email, password = pass))
    }

    fun register(
        register: Register,
        file: MultipartBody.Part?
    ): Observable<Register> {
        val json = Gson().toJson(register)
//        val mediaType = "application/json; charset=utf-8".toMediaType()
        val mediaType = "text/plain".toMediaTypeOrNull()
        val requestBody = json.toRequestBody(mediaType)
        return apiServices.register(
            register.firstName?.toRequestBody(mediaType),
            register.dateOfBirth?.toRequestBody(mediaType),
            register.address?.toRequestBody(mediaType),
            register.district.toString().toRequestBody(mediaType),
            register.city.toString().toRequestBody(mediaType),
            register.phone?.toRequestBody(mediaType),
            register.email?.toRequestBody(mediaType),
            register.pass?.toRequestBody(mediaType),
            register.lastName?.toRequestBody(mediaType),
            file
        )
    }

    fun editUser(userId: Int, register: Register): Observable<Register> =
        apiServices.editUser(userId, register)

    fun changePass(
        token: String,
        userId: Int,
        email: String,
        oldPass: String,
        newPass: String
    ) = apiServices.changePass(token, userId, ChangePassModel(email, oldPass, newPass))

    fun loginWithGoogle(user: User) = apiServices.loginWithGoogle(user)
    fun getImages(apartmentId: Int) = apiServices.getImages(apartmentId)
    fun sendRating(token: String, apartmentId: Int, commentResponse: CommentResponse) =
        apiServices.sendRating(token, apartmentId, commentResponse)

    fun getUserApartment(token: String, userId: Int) = apiServices.getUserApartment(token, userId)
    fun editApartment(token: String, apartmentResponse: ApartmentResponse) =
        apiServices.editApartment(token, apartmentResponse)

}


