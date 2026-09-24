package com.mody.recipefinder.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.domain.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val meal: Meal? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class DetailViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    /**
     * @param id          meal id from nav argument
     * @param initialName if we came from a search (which only has id/name/thumb),
     *                    we can show the name instantly while the full detail loads.
     */
    fun loadMeal(id: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            repository.getMealById(id)
                .onSuccess { meal ->
                    _uiState.value = _uiState.value.copy(
                        meal = meal,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to load recipe"
                    )
                }
        }

        // Observe favorite status — auto-updates when we add/remove.
        viewModelScope.launch {
            repository.observeIsFavorite(id).collect { isFav ->
                _uiState.value = _uiState.value.copy(isFavorite = isFav)
            }
        }
    }

    fun toggleFavorite() {
        val meal = _uiState.value.meal ?: return
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                repository.removeFavorite(meal.id)
            } else {
                repository.addFavorite(meal)
            }
            // No manual state update — observeIsFavorite will emit the new value.
        }
    }
}
