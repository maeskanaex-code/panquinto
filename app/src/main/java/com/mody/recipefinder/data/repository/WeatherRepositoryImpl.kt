package com.mody.recipefinder.data.repository

import com.mody.recipefinder.BuildConfig
import com.mody.recipefinder.data.mapper.toDomain
import com.mody.recipefinder.data.remote.WeatherApiService
import com.mody.recipefinder.domain.model.Weather

/**
 * Talks to WeatherAPI.com.
 *
 * The API key comes from BuildConfig.WEATHER_API_KEY — set in
 * gradle.properties, never committed to Git.
 *
 * Wraps the call in runCatching so the ViewModel gets a Result instead
 * of an exception. Weather failing should never crash the app or block
 * the meal grid — it just means the weather card stays hidden.
 */
class WeatherRepositoryImpl(
    private val api: WeatherApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): Result<Weather> =
        runCatching {
            api.getCurrentWeather(
                apiKey = BuildConfig.WEATHER_API_KEY,
                city = city
            ).toDomain()
        }
}
