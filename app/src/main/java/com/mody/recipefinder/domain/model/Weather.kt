package com.mody.recipefinder.domain.model

/**
 * Clean domain model for weather.
 *
 * The UI never sees the nested DTO structure. It sees this flat object:
 * temperature, a human-readable description, and a condition code we can
 * match on for meal suggestions.
 */
data class Weather(
    val cityName: String,
    val country: String,
    val temperatureCelsius: Double,
    val conditionText: String,
    val conditionCode: Int
)
