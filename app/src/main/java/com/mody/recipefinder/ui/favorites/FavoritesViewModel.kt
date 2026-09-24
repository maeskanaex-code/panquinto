package com.mody.recipefinder.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.domain.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val favorites: List<Meal> = emptyList()
)

class FavoritesViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        // Subscribe to Room's Flow. Every insert/delete emits a new list;
        // we copy it into uiState and Compose recomposes automatically.
        viewModelScope.launch {
            repository.observeFavorites().collect { meals ->
                _uiState.value = FavoritesUiState(favorites = meals)
            }
        }
    }

    fun removeFavorite(meal: Meal) {
        viewModelScope.launch {
            repository.removeFavorite(meal.id)
            // No manual refresh — the Flow above emits again.
        }
    }
}
