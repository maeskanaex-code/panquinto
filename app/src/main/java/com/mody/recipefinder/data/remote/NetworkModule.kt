package com.mody.recipefinder.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Manual dependency wiring for the network layer.
 *
 * Two Retrofit instances: one per base URL. Retrofit cannot switch base
 * URLs per-call, so two APIs means two instances.
 *
 * Both share the same OkHttp client so the logging interceptor and timeouts
 * are configured in exactly one place.
 */
object NetworkModule {

    private const val MEALDB_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    private const val WEATHER_BASE_URL = "https://api.weatherapi.com/v1/"

    /**
     * Logs full request/response bodies to Logcat under tag "OkHttp".
     * Level BODY is verbose but invaluable for portfolio screenshots.
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

    val mealApiService: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl(MEALDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }

    val weatherApiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}
