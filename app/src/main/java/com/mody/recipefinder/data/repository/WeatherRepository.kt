package com.mody.recipefinder.data.repository

import com.mody.recipefinder.domain.model.Weather

/**
 * Contract for weather data.
 *
 * The ViewModel depends on this interface, not on Retrofit. If we later
 * swap WeatherAPI.com for OpenWeatherMap, only the implementation changes.
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): Result<Weather>
}
