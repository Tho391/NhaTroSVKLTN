package com.thomas.apps.nhatrosvkltn.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitClientInstance {
    private lateinit var retrofit: Retrofit
    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    companion object {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

//        fun create(): ApartmentsServiceClient {
//            val retrofit = Retrofit.Builder()
//                .addCallAdapterFactory(.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://lit-eyrie-49840.herokuapp.com/")
//                .client(client)
//                .build()
//            return retrofit.create(ApartmentsServiceClient::class.java)
//        }
    }
}