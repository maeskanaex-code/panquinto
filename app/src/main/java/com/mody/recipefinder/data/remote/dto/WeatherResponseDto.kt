package com.mody.recipefinder.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Top-level response from WeatherAPI.com's current.json endpoint.
 *
 * Shape:
 * {
 *   "location": { "name": "Alexandria", "country": "Egypt", ... },
 *   "current":  { "temp_c": 24.0, "condition": { "text": "Partly cloudy", "code": 1003 } }
 * }
 *
 * We only need a small part of it. Gson ignores unknown fields by default,
 * so we declare only what we use.
 */
data class WeatherResponseDto(
    @SerializedName("location") val location: LocationDto,
    @SerializedName("current")  val current: CurrentDto
)

data class LocationDto(
    @SerializedName("name")    val name: String,
    @SerializedName("country") val country: String
)

data class CurrentDto(
    @SerializedName("temp_c")    val tempC: Double,
    @SerializedName("condition") val condition: ConditionDto
)

data class ConditionDto(
    @SerializedName("text") val text: String,
    @SerializedName("code") val code: Int
)
