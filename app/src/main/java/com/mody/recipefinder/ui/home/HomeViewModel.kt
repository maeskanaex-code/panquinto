package com.mody.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mody.recipefinder.domain.model.Category
import com.mody.recipefinder.domain.model.Meal
import com.mody.recipefinder.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * State for the Home screen.
 */
data class HomeUiState(
    val query: String = "",
    val categories: List<Category> = emptyList(),
    val searchResults: List<Meal> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HomeViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.observeFavorites().collect { favorites ->
                _uiState.value = _uiState.value.copy(
                    favoriteIds = favorites.map { it.id }.toSet()
                )
            }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            if (_uiState.value.favoriteIds.contains(meal.id)) {
                repository.removeFavorite(meal.id)
            } else {
                repository.addFavorite(meal)
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
    }

    fun search(query: String = _uiState.value.query) {
        if (query.isBlank()) return
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            repository.searchMealsByName(query)
                .onSuccess { meals ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = meals,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Search failed"
                    )
                }
        }
    }

    fun loadCategory(categoryName: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            repository.filterMealsByCategory(categoryName)
                .onSuccess { meals ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = meals,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to load category"
                    )
                }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories()
                .onSuccess { categories ->
                    _uiState.value = _uiState.value.copy(categories = categories)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
