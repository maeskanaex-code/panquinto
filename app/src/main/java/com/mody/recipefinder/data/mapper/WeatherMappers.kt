package com.mody.recipefinder.data.mapper

import com.mody.recipefinder.data.remote.dto.WeatherResponseDto
import com.mody.recipefinder.domain.model.Weather

/**
 * Maps the raw WeatherAPI.com response to our clean domain model.
 *
 * Flattens the nested location / current / condition structure into a
 * single object with everything the UI needs.
 */
fun WeatherResponseDto.toDomain(): Weather = Weather(
    cityName = location.name,
    country = location.country,
    temperatureCelsius = current.tempC,
    conditionText = current.condition.text,
    conditionCode = current.condition.code
)
