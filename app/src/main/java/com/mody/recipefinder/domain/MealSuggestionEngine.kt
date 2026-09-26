package com.mody.recipefinder.domain

/**
 * Maps a weather condition to a meal category.
 *
 * This is the "intelligence" of the weather feature — a small rule engine
 * that turns a raw weather code into a suggestion a human can act on.
 *
 * WeatherAPI.com uses "condition codes" that are similar to WMO codes.
 * Rather than mapping every one of the ~50 codes individually, we bucket
 * them into a handful of sensible categories and match on those.
 *
 * Codes reference: https://www.weatherapi.com/docs/weather_conditions.json
 */
object MealSuggestionEngine {

    /**
     * Given a weather condition code and temperature, return a meal
     * category string that the app can use to filter TheMealDB.
     */
    fun suggestCategory(conditionCode: Int, temperatureCelsius: Double): String {
        return when {
            // Rain, drizzle, thunderstorm → comfort food
            isRainy(conditionCode) -> "Beef"

            // Snow, freezing → hearty, warm food
            isSnowy(conditionCode) -> "Beef"

            // Clear + hot → light, cold dishes
            isClear(conditionCode) && temperatureCelsius >= 25.0 -> "Seafood"

            // Clear + cold → warm, substantial meals
            isClear(conditionCode) && temperatureCelsius <= 10.0 -> "Beef"

            // Clear + mild → general main dishes
            isClear(conditionCode) -> "Chicken"

            // Cloudy, overcast, mist, fog → neutral, any main
            else -> "Chicken"
        }
    }

    /**
     * Given a weather condition code and temperature, return a short
     * human-readable reason for the suggestion. Shown on the weather card.
     */
    fun suggestReason(conditionCode: Int, temperatureCelsius: Double): String {
        return when {
            isRainy(conditionCode)  -> "Warm and comforting for a rainy day"
            isSnowy(conditionCode)  -> "Hearty meal for cold weather"
            isClear(conditionCode) && temperatureCelsius >= 25.0 ->
                "Light and fresh for a hot day"
            isClear(conditionCode) && temperatureCelsius <= 10.0 ->
                "Something warm for a chilly day"
            isClear(conditionCode)  -> "A good day for a classic meal"
            else                     -> "Something tasty for today"
        }
    }

    // --- Private helpers ---

    private fun isRainy(code: Int): Boolean =
        code in 1063..1201   // drizzle, rain, thunderstorm range

    private fun isSnowy(code: Int): Boolean =
        code in 1066..1258   // snow, blizzard, freezing range

    private fun isClear(code: Int): Boolean =
        code == 1000         // WeatherAPI uses 1000 for "Sunny / Clear"
}
