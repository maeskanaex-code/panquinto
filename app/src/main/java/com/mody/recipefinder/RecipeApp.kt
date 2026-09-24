package com.mody.recipefinder

import android.app.Application
import com.mody.recipefinder.data.local.AppDatabase
import com.mody.recipefinder.data.remote.NetworkModule
import com.mody.recipefinder.data.repository.MealRepositoryImpl

/**
 * Application subclass. Instantiated once before any Activity.
 *
 * Holds the singletons the whole app shares — Room database, Retrofit
 * service, and the repository. This is manual dependency injection.
 */
class RecipeApp : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    val repository: MealRepositoryImpl by lazy {
        MealRepositoryImpl(
            api = NetworkModule.mealApiService,
            dao = database.favoriteMealDao()
        )
    }
}
