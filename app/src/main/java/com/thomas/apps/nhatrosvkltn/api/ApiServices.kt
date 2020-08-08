package com.thomas.apps.nhatrosvkltn.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.APARTMENT
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.APARTMENTS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.CHANGE_PASS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.COMMENTS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.EDIT_APARTMENT
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.EDIT_USER
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.FILTER
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.IMAGES
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.LOGIN
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.LOGIN_GOOGLE
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.RATING
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.RECOMMEND
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.REGISTER
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.SEARCH
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.UP_APARTMENT
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.USER_APARTMENTS
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import com.thomas.apps.nhatrosvkltn.model.servermodel.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface ApiServices {

    @GET(APARTMENTS)
    fun getApartments(): Observable<List<ApartmentResponse>>

    @GET(APARTMENT)
    fun getApartment(
        @Path("id") apartmentId: Int
    ): Observable<ApartmentResponse>

    @GET(IMAGES)
    fun getImages(@Path("id") apartmentId: Int): Observable<List<ImageResponse>>

    @POST(FILTER)
    fun filterApartments(
        @Body filterModel: FilterModel
    ): Observable<List<ApartmentResponse>>

    @GET(SEARCH)
    fun searchApartments(
        @Query("address") query: String
    ): Observable<List<ApartmentResponse>>

    @POST(APARTMENTS)
    fun postApartment(
        @Header("authorization") token: String,
        @Body apartment: ApartmentResponse
    ): Observable<ApartmentResponse>

    /**
     * Post apartment when server store image
     */
    @Multipart
    @POST(UP_APARTMENT)
    fun postApartment(
        @Header("authorization") token: String,
        @Path("userId") userId: Int,
        @Part image: List<MultipartBody.Part>,
        @Part("Tenchutro") owner: RequestBody?,
        @Part("Sdt") phone: RequestBody?,
        @Part("Diachi") address: RequestBody?,
        @Part("idQuan") districtId: RequestBody?,
        @Part("idThanhpho") cityId: RequestBody?,
        @Part("LocalX") latitude: RequestBody?,
        @Part("LocalY") longitude: RequestBody?,
        @Part("Date") date: RequestBody?,
        @Part("Title") title: RequestBody?,
        @Part("Dientich") area: RequestBody?,
        @Part("Phong") room: RequestBody?,
        @Part("Nhavesinh") toilet: RequestBody?,
        @Part("Mota") description: RequestBody?,
        @Part("Gia") price: RequestBody?,
        @Part("Nuocnong") heater: RequestBody?,
        @Part("Dien") electric: RequestBody?,
        @Part("Nuoc") water: RequestBody?,
        @Part("Loainha") type: RequestBody?,
        @Part("wifi") wifi: RequestBody?,
        @Part("gio") time: RequestBody?,
        @Part("chungchu") key: RequestBody?,
        @Part("Maylanh") air: RequestBody?,
        @Part("Giuxe") parking: RequestBody?
    ): Observable<ApartmentResponse>

    @POST(UP_APARTMENT)
    fun postApartment(
        @Header("authorization") token: String,
        @Path("userId") userId: Int,
        @Body apartmentResponse: ApartmentResponse
    )

    //todo add apartment id as query
    @GET(COMMENTS)
    fun getComments(
        @Path("id") apartmentId: Int
    ): Observable<List<CommentResponse>>

    @POST(COMMENTS)
    fun sendComment(
        @Header("authorization") token: String,
        @Path("id") apartmentId: Int,
        @Body comment: CommentResponse
    ): Observable<CommentResponse>

    @POST(LOGIN)
    fun login(
        @Body loginResponse: LoginResponse
    ): Observable<LoginResponse>

    @Multipart
    @POST(REGISTER)
    fun register(
        @Part("Ten") name: RequestBody?,
        @Part("NgaySinh") dateOfBirth: RequestBody?,
        @Part("DiaChi") address: RequestBody?,
        @Part("idQuan") districtId: RequestBody?,
        @Part("idThanhPho") city: RequestBody?,
        @Part("Sdt") Sdt: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("pass") pass: RequestBody?,
        @Part("Ho") lastName: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Observable<Register>

    @POST(REGISTER)
    fun register2(@Body register: Register): Observable<Register>

    @POST(EDIT_USER)
    fun editUser(
        @Header("authorization") token: String,
        @Path("id") userId: Int,
        @Body register: Register
    ): Observable<Register>


    @POST(CHANGE_PASS)
    fun changePass(
        @Header("authorization") token: String,
        @Path("id") userId: Int,
        @Body changePassModel: ChangePassModel
    ): Observable<Register>

    @POST(LOGIN_GOOGLE)
    fun loginWithGoogle(@Body register: Register): Observable<LoginResponse>

    @POST(RATING)
    fun sendRating(
        @Header("authorization") token: String,
        @Path("id") apartmentId: Int,
        @Body comment: CommentResponse
    ): Observable<LoginResponse>

    @GET(USER_APARTMENTS)
    fun getUserApartment(
        @Header("authorization") token: String,
        @Path("id") userId: Int
    ): Observable<List<ApartmentResponse>>

    @POST(EDIT_APARTMENT)
    fun editApartment(
        @Header("authorization") token: String,
        @Path("id") apartmentId: Int,
        @Body apartmentResponse: ApartmentResponse
    ): Observable<ApartmentResponse>

    @GET(RECOMMEND)
    fun recommend(
        @Path("id") cityId: Int
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
                .client(getUnsafeOkHttpClient())
                .build()

            return retrofit.create(ApiServices::class.java)
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient? {
            try {
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {

                        }

                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {

                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }

                    }
                )

                // Install the all-trusting trust manager

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, (trustAllCerts[0] as X509TrustManager))
//                builder.hostnameVerifier(object : HostnameVerifier {
//                    override fun verify(hostname: String?, session: SSLSession?): Boolean {
//                        return true
//                    }
//
//                })


                return builder.addInterceptor(logging)
                    //todo remove time out
                    .connectTimeout(10 * 60, TimeUnit.SECONDS)
                    .writeTimeout(10 * 60, TimeUnit.SECONDS)
                    .readTimeout(30 * 60, TimeUnit.SECONDS)

                    .build()
            } catch (e: Exception) {
                Log.e("lỗi retrofit", e.message + "")
            }
            return null
        }
    }


}