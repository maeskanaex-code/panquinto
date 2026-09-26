package com.mody.recipefinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.data.repository.WeatherRepository
import com.mody.recipefinder.ui.assistant.AssistantViewModel
import com.mody.recipefinder.ui.detail.DetailViewModel
import com.mody.recipefinder.ui.favorites.FavoritesViewModel
import com.mody.recipefinder.ui.home.HomeViewModel

class RecipeFinderViewModelFactory(
    private val mealRepository: MealRepository,
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(mealRepository, weatherRepository) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(mealRepository) as T

            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(mealRepository) as T

            modelClass.isAssignableFrom(AssistantViewModel::class.java) ->
                AssistantViewModel(mealRepository) as T

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
