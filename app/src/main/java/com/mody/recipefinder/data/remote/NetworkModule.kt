package com.mody.recipefinder.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Manual dependency wiring for the network layer.
 *
 * Why manual: for a 4-screen MVP, Hilt/Dagger is ceremony without benefit.
 * This object is the single place that knows how to build a Retrofit
 * instance — swap the base URL or add interceptors here, nowhere else.
 */
object NetworkModule {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    /**
     * Logs full request/response bodies to Logcat under tag "OkHttp".
     * Level BODY is verbose but invaluable while developing and for
     * portfolio evidence.
     */
    private val loggingInterceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * lazy: the Retrofit instance is built once, the first time someone
     * accesses MealApiService. Every subsequent access reuses it.
     */
    val mealApiService: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }
}
