package com.mody.recipefinder.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Wrapper for the top-level JSON TheMealDB returns for meal endpoints.
 * Example: { "meals": [ {...}, {...} ] }
 *
 * Nullable because TheMealDB returns {"meals": null} (not an empty array)
 * when a search has no results. A non-null type would crash Gson on
 * "no results" queries.
 */
data class MealResponse(
    @SerializedName("meals")
    val meals: List<MealDto>?
)
