package com.mody.recipefinder.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for a favorited meal.
 *
 * Why @PrimaryKey on id: TheMealDB's idMeal is already unique, so we reuse it.
 * Inserting the same id twice will REPLACE the row (see the DAO's onConflict).
 *
 * Why ingredients stored as a single String:
 *   Room doesn't natively store List<Ingredient>. Options were:
 *     a) A second table + @Relation (correct, but more code)
 *     b) A TypeConverter that serializes to/from JSON (clean, needs Gson)
 *     c) Flatten to a plain string with a separator (what we're doing)
 *   For an MVP, (c) is fastest to build and explain. The separator "|||"
 *   is unlikely to appear in ingredient names or measures.
 *
 *   When you want to level up: swap to a @TypeConverter using Gson.
 *   That's a good follow-up PR for the portfolio.
 */
@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbnail: String,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val ingredientsFlattened: String
)
