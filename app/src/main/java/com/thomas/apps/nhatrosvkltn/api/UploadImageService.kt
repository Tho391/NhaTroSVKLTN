package com.thomas.apps.nhatrosvkltn.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.thomas.apps.nhatrosvkltn.model.imgbb.ImgbbResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadImageService {

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part("key") key: RequestBody,
        @Part image: MultipartBody.Part
    ): Observable<ImgbbResponse>


    companion object {
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): UploadImageService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.IMGBB_URL)
                .client(client)
                .build()

            return retrofit.create(UploadImageService::class.java)
        }
    }
}