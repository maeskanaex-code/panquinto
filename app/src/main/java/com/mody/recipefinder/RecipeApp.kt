package com.mody.recipefinder

import android.app.Application
import com.mody.recipefinder.data.local.AppDatabase
import com.mody.recipefinder.data.preferences.ThemePreferences
import com.mody.recipefinder.data.remote.NetworkModule
import com.mody.recipefinder.data.repository.MealRepositoryImpl
import com.mody.recipefinder.data.repository.WeatherRepositoryImpl

/**
 * Application subclass. Instantiated once before any Activity.
 *
 * Holds the singletons the whole app shares — Room database, both Retrofit
 * services, both repositories, and theme preferences.
 */
class RecipeApp : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    val mealRepository: MealRepositoryImpl by lazy {
        MealRepositoryImpl(
            api = NetworkModule.mealApiService,
            dao = database.favoriteMealDao()
        )
    }

    val weatherRepository: WeatherRepositoryImpl by lazy {
        WeatherRepositoryImpl(
            api = NetworkModule.weatherApiService
        )
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this)
    }
}
