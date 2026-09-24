package com.mody.recipefinder.data.repository

import com.mody.recipefinder.data.local.FavoriteMealDao
import com.mody.recipefinder.data.local.FavoriteMealEntity
import com.mody.recipefinder.data.mapper.toDomain
import com.mody.recipefinder.data.mapper.toEntity
import com.mody.recipefinder.data.remote.MealApiService
import com.mody.recipefinder.domain.model.Category
import com.mody.recipefinder.domain.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation that talks to TheMealDB (remote) and Room (local).
 *
 * Remote calls wrap errors in Result so the ViewModel never sees
 * IOException, HttpException, or JSON errors directly.
 * Favorites are exposed as Flow so the UI auto-updates.
 */
class MealRepositoryImpl(
    private val api: MealApiService,
    private val dao: FavoriteMealDao
) : MealRepository {

    // --- Remote ---

    override suspend fun searchMealsByName(name: String): Result<List<Meal>> =
        runCatching {
            api.searchMealsByName(name)
                .meals
                .orEmpty()
                .map { it.toDomain() }
        }

    override suspend fun filterMealsByIngredient(ingredient: String): Result<List<Meal>> =
        runCatching {
            api.filterMealsByIngredient(ingredient)
                .meals
                .orEmpty()
                .map { it.toDomain() }
        }

    override suspend fun filterMealsByCategory(category: String): Result<List<Meal>> =
        runCatching {
            api.filterMealsByCategory(category)
                .meals
                .orEmpty()
                .map { it.toDomain() }
        }

    override suspend fun getMealById(id: String): Result<Meal?> =
        runCatching {
            api.getMealById(id)
                .meals
                ?.firstOrNull()
                ?.toDomain()
        }

    override suspend fun getRandomMeal(): Result<Meal?> =
        runCatching {
            api.getRandomMeal()
                .meals
                ?.firstOrNull()
                ?.toDomain()
        }

    override suspend fun getCategories(): Result<List<Category>> =
        runCatching {
            api.getCategories()
                .categories
                .orEmpty()
                .map { it.toDomain() }
        }

    // --- Local ---

    override fun observeFavorites(): Flow<List<Meal>> =
        dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun observeIsFavorite(id: String): Flow<Boolean> =
        dao.observeIsFavorite(id)

    override suspend fun addFavorite(meal: Meal): Result<Unit> =
        runCatching {
            dao.insert(meal.toEntity())
        }

    override suspend fun removeFavorite(id: String): Result<Unit> =
        runCatching {
            dao.deleteById(id)
        }
}
