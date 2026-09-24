package com.mody.recipefinder.data.repository

import com.mody.recipefinder.domain.model.Category
import com.mody.recipefinder.domain.model.Meal
import kotlinx.coroutines.flow.Flow

/**
 * Contract the ViewModel depends on.
 *
 * The ViewModel never imports Retrofit, OkHttp, or Room — it only knows
 * this interface. That is what makes it testable.
 *
 * Remote functions return Result<T> so the ViewModel handles success/
 * failure explicitly. Local favorites return Flow<T> so the UI observes
 * changes automatically when the DB updates.
 */
interface MealRepository {

    // --- Remote (TheMealDB) ---

    suspend fun searchMealsByName(name: String): Result<List<Meal>>

    suspend fun filterMealsByIngredient(ingredient: String): Result<List<Meal>>

    suspend fun filterMealsByCategory(category: String): Result<List<Meal>>

    suspend fun getMealById(id: String): Result<Meal?>

    suspend fun getRandomMeal(): Result<Meal?>

    suspend fun getCategories(): Result<List<Category>>

    // --- Local (Room) ---

    fun observeFavorites(): Flow<List<Meal>>

    fun observeIsFavorite(id: String): Flow<Boolean>

    suspend fun addFavorite(meal: Meal): Result<Unit>

    suspend fun removeFavorite(id: String): Result<Unit>
}
