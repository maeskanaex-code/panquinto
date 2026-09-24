package com.mody.recipefinder.data.remote

import com.mody.recipefinder.data.remote.dto.CategoryResponse
import com.mody.recipefinder.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for TheMealDB.
 *
 * Base URL: https://www.themealdb.com/api/json/v1/1/
 * Every call is a suspend function — Retrofit 2.6+ handles the background
 * threading. We call these from a coroutine.
 *
 * TheMealDB uses query params for everything (?s=, ?i=, ?c=), not
 * REST-style paths. So no @Path here — only @Query.
 */
interface MealApiService {

    /** Search meals by name. Example: s=chicken */
    @GET("search.php")
    suspend fun searchMealsByName(
        @Query("s") name: String
    ): MealResponse

    /** Filter meals by main ingredient. Example: i=chicken_breast */
    @GET("filter.php")
    suspend fun filterMealsByIngredient(
        @Query("i") ingredient: String
    ): MealResponse

    /** Filter meals by category. Example: c=Seafood */
    @GET("filter.php")
    suspend fun filterMealsByCategory(
        @Query("c") category: String
    ): MealResponse

    /** Fetch full meal details by ID. Example: i=52772 */
    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): MealResponse

    /** Fetch one random meal. No parameters. */
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    /** List all categories (Beef, Chicken, Dessert, Seafood, ...). */
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}
