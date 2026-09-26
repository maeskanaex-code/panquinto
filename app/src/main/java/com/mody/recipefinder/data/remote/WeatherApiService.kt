package com.mody.recipefinder.data.remote

import com.mody.recipefinder.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for WeatherAPI.com.
 *
 * Base URL: https://api.weatherapi.com/v1/
 * Endpoint: current.json — current weather for a location.
 *
 * The API key is passed as a query parameter (?key=...), not a header.
 * We supply it from BuildConfig so the actual value never appears in
 * this file or in Git.
 */
interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q")   city: String,
        @Query("aqi") aqi: String = "no"
    ): WeatherResponseDto
}
