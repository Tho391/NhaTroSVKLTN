package com.thomas.apps.nhatrosvkltn.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.COMMENTS_TEST
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.KEY_TEST
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.TEST_APARTMENTS
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.TEST_APARTMENT_ID
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.TEST_SEARCH_APARTMENT
import com.thomas.apps.nhatrosvkltn.api.Urls.Companion.TEST_URL
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServicesTest {

    @GET(TEST_APARTMENTS)
    fun getApartments(@Query("key") key: String = KEY_TEST): Observable<List<Apartment>>

    @GET(TEST_APARTMENT_ID)
    fun getApartment(
        @Path("id") apartmentId: Int,
        @Query("key") key: String = KEY_TEST
    ): Observable<Apartment>

    @POST(TEST_SEARCH_APARTMENT)
    fun filterApartments(
        @Body filterModel: FilterModel,
        @Query("key") key: String = KEY_TEST
    ): Observable<List<Apartment>>

    @POST(TEST_SEARCH_APARTMENT)
    fun searchApartments(
        @Body query: String,
        @Query("key") key: String = KEY_TEST
    ): Observable<List<Apartment>>

    @POST(TEST_APARTMENTS)
    fun postApartment(
        @Body apartment: Apartment,
        @Query("key") key: String = KEY_TEST
    ): Observable<Apartment>

    @GET(COMMENTS_TEST)
    fun getComments(
//        apartmentId: Int,
        @Query("key") key: String = KEY_TEST
    ): Observable<List<Comment>>

    @POST(COMMENTS_TEST)
    fun sendComment(@Body comment: Comment): Observable<Comment>


    companion object {
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): ApiServicesTest {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TEST_URL)
                .client(client)
                .build()

            return retrofit.create(ApiServicesTest::class.java)
        }
    }
}