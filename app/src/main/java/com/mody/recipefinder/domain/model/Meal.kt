package com.mody.recipefinder.domain.model

/**
 * Clean domain model used by ViewModels and Compose UI.
 *
 * The UI never sees MealDto or the ingredient1..20 slots. It sees this:
 * ingredients as a proper List, everything typed, nullables only where
 * TheMealDB genuinely returns nothing.
 */
data class Meal(
    val id: String,
    val name: String,
    val thumbnail: String,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val ingredients: List<Ingredient>
)
