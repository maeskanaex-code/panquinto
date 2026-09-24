package com.mody.recipefinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.ui.detail.DetailViewModel
import com.mody.recipefinder.ui.favorites.FavoritesViewModel
import com.mody.recipefinder.ui.home.HomeViewModel

/**
 * Manual ViewModel factory.
 *
 * ViewModels with constructor arguments (our repository) need a factory
 * because the default one only handles no-arg VMs.
 *
 * Why manual: for 4 ViewModels, Hilt's @HiltViewModel + @Inject is a lot
 * of ceremony. This class is 30 lines, explicit, and easy to explain.
 *
 * Usage in a Composable:
 *   val vm: HomeViewModel = viewModel(
 *       factory = RecipeFinderViewModelFactory(repository)
 *   )
 */
class RecipeFinderViewModelFactory(
    private val repository: MealRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(repository) as T

            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(repository) as T

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
