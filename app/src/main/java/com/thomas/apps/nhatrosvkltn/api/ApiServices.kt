package com.thomas.apps.nhatrosvkltn.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.APARTMENT
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.APARTMENTS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.CHANGE_PASS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.COMMENTS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.EDIT_USER
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.IMAGES
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.LOGIN
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.LOGIN_GOOGLE
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.RATING
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.REGISTER
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.USER_APARTMENTS
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServices {

    @GET(APARTMENTS)
    fun getApartments(): Observable<List<ApartmentResponse>>

    @GET(APARTMENT)
    fun getApartment(
        @Path("id") apartmentId: Int
    ): Observable<ApartmentResponse>

    @GET(IMAGES)
    fun getImages(@Path("id") apartmentId: Int): Observable<List<ImageResponse>>

    @POST(Urls.TEST_SEARCH_APARTMENT)
    fun filterApartments(
        @Body filterModel: FilterModel
    ): Observable<List<ApartmentResponse>>

    @POST(Urls.TEST_SEARCH_APARTMENT)
    fun searchApartments(
        @Body query: String
    ): Observable<List<ApartmentResponse>>

    @POST(APARTMENTS)
    fun postApartment(
        @Header("authorization") token: String,
        @Body apartment: ApartmentResponse
    ): Observable<ApartmentResponse>

    @Multipart
    @POST(APARTMENTS)
    fun postApartment(
        @Header("authorization") token: String,
        @Part("fileName") fileName: RequestBody,
        @Part image: MultipartBody.Part,
        @Part apartment: ApartmentResponse
    ): Observable<ApartmentResponse>

    //todo add apartment id as query
    @GET(COMMENTS)
    fun getComments(
        @Path("id") apartmentId: Int
    ): Observable<List<CommentResponse>>

    @POST(COMMENTS)
    fun sendComment(
        @Header("authorization") token: String,
        @Body comment: CommentResponse
    ): Observable<CommentResponse>

    @POST(LOGIN)
    fun login(
        @Body loginResponse: LoginResponse
    ): Observable<LoginResponse>

    @Multipart
    @POST(REGISTER)
    fun register(
        @Body register: Register,
        @Part("fileavatar") fileName: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Observable<Register>

    @POST(EDIT_USER)
    fun editUser(@Body register: Register): Observable<Register>

    @FormUrlEncoded
    @POST(CHANGE_PASS)
    fun changePass(
        @Field("token") token: String,
        @Field("Username") email: String,
        @Field("current_password") oldPass: String,
        @Field("new_password") newPass: String
    ): Observable<Register>

    @POST(LOGIN_GOOGLE)
    fun loginWithGoogle(@Body user: User): Observable<LoginResponse>

    @FormUrlEncoded
    @POST(RATING)
    fun sendRating(
        @Header("authorization") token: String,
        @Path("id") apartmentId: Int,
        @Field("rating") rating: Float
    ): Observable<LoginResponse>

    @GET(USER_APARTMENTS)
    fun getUserApartment(
        @Header("authorization") token: String,
        @Path("id") userId: Int
    ): Observable<List<ApartmentResponse>>

    companion object {
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): ApiServices {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}