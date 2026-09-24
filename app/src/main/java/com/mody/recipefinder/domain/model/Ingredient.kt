package com.mody.recipefinder.domain.model

/**
 * One ingredient with its measurement.
 * Produced by the mapper from a strIngredientN / strMeasureN pair.
 * Example: Ingredient(name = "Chicken Breast", measure = "500g")
 */
data class Ingredient(
    val name: String,
    val measure: String
)
