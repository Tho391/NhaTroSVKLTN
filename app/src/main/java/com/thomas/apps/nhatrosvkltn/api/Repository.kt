package com.thomas.apps.nhatrosvkltn.api

import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.model.imgbb.ImgbbResponse
import com.thomas.apps.nhatrosvkltn.model.servermodel.*
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*

class Repository {
    companion object {
        val apiServices by lazy {
            ApiServices.create()
        }
        val uploadImageService by lazy {
            UploadImageService.create()
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
        userId: Int,
        file: List<MultipartBody.Part>,
        apartment: ApartmentResponse
    ): Observable<ApartmentResponse> {
        val mediaType = "text/plain".toMediaTypeOrNull()
        return apiServices.postApartment(
            token,
            userId,
            file,
            apartment.tenchutro.toRequestBody(mediaType),
            apartment.sdt.toRequestBody(mediaType),
            apartment.diachi.toRequestBody(mediaType),
            apartment.idQuan.toString().toRequestBody(mediaType),
            1.toString().toRequestBody(mediaType),
            apartment.localX.toString().toRequestBody(mediaType),
            apartment.localY.toString().toRequestBody(mediaType),
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                .toRequestBody(mediaType),
            apartment.title.toRequestBody(mediaType),
            apartment.dientich.toString().toRequestBody(mediaType),
            apartment.phong.toString().toRequestBody(mediaType),
            apartment.nhavesinh.toString().toRequestBody(mediaType),
            apartment.mota.toRequestBody(mediaType),
            apartment.gia.toString().toRequestBody(mediaType),
            apartment.nuocnong.toString().toRequestBody(mediaType),
            apartment.dien.toString().toRequestBody(mediaType),
            apartment.nuoc.toString().toRequestBody(mediaType),
            apartment.loainha.toRequestBody(mediaType),
            apartment.wifi.toString().toRequestBody(mediaType),
            apartment.time.toString().toRequestBody(mediaType),
            apartment.key.toString().toRequestBody(mediaType),
            apartment.maylanh.toString().toRequestBody(mediaType),
            apartment.giuxe.toString().toRequestBody(mediaType)
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

    /**
     * register with server store image
     */
    fun register(
        register: Register,
        file: MultipartBody.Part?
    ): Observable<Register> {
        val mediaType = "text/plain".toMediaTypeOrNull()
        return apiServices.register(
            register.firstName?.toRequestBody(mediaType),
            register.dateOfBirth?.toRequestBody(mediaType),
            register.address?.toRequestBody(mediaType),
            register.districtId.toString().toRequestBody(mediaType),
            register.city.toString().toRequestBody(mediaType),
            register.phone?.toRequestBody(mediaType),
            register.email?.toRequestBody(mediaType),
            register.pass?.toRequestBody(mediaType),
            register.lastName?.toRequestBody(mediaType),
            file
        )
    }

    /**
     * register with server store url
     */
    fun register2(register: Register) = apiServices.register2(register)

    fun editUser(token: String, userId: Int, register: Register) =
        apiServices.editUser(token, userId, register)

    fun changePass(
        token: String,
        userId: Int,
        email: String,
        oldPass: String,
        newPass: String
    ) = apiServices.changePass(token, userId, ChangePassModel(email, oldPass, newPass))

    fun loginWithGoogle(register: Register) = apiServices.loginWithGoogle(register)
    fun getImages(apartmentId: Int) = apiServices.getImages(apartmentId)
    fun sendRating(token: String, apartmentId: Int, commentResponse: CommentResponse) =
        apiServices.sendRating(token, apartmentId, commentResponse)

    fun getUserApartment(token: String, userId: Int) = apiServices.getUserApartment(token, userId)
    fun editApartment(token: String, apartmentResponse: ApartmentResponse) =
        apiServices.editApartment(token, apartmentResponse.idNhatro, apartmentResponse)

    //todo hiện tại mới làm hcm id = 1
    fun recommend(cityId: Int) = apiServices.recommend(1)

    fun uploadImage(key: String, file: MultipartBody.Part): Observable<ImgbbResponse> {
        val mediaType = "text/plain".toMediaTypeOrNull()
        return uploadImageService.uploadImage(
            key = key.toRequestBody(mediaType),
            image = file
        )
    }

    fun postApartment2(
        token: String,
        userId: Int,
        apartmentResponse: ApartmentResponse
    ) = apiServices.postApartment2(token, userId, apartmentResponse)

}


