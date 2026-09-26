package com.mody.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.data.repository.WeatherRepository
import com.mody.recipefinder.domain.MealSuggestionEngine
import com.mody.recipefinder.domain.model.Category
import com.mody.recipefinder.domain.model.Meal
import com.mody.recipefinder.domain.model.Weather
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
    val errorMessage: String? = null,

    // --- Weather feature ---
    val weather: Weather? = null,
    val suggestedMeal: Meal? = null,
    val suggestionReason: String? = null,
    val isWeatherLoading: Boolean = false
)

class HomeViewModel(
    private val mealRepository: MealRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
        observeFavorites()
        loadWeatherAndSuggestion()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            mealRepository.observeFavorites().collect { favorites ->
                _uiState.value = _uiState.value.copy(
                    favoriteIds = favorites.map { it.id }.toSet()
                )
            }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            if (_uiState.value.favoriteIds.contains(meal.id)) {
                mealRepository.removeFavorite(meal.id)
            } else {
                mealRepository.addFavorite(meal)
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
            mealRepository.searchMealsByName(query)
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
            mealRepository.filterMealsByCategory(categoryName)
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
            mealRepository.getCategories()
                .onSuccess { categories ->
                    _uiState.value = _uiState.value.copy(categories = categories)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    // --- Weather feature ---

    private fun loadWeatherAndSuggestion() {
        _uiState.value = _uiState.value.copy(isWeatherLoading = true)

        viewModelScope.launch {
            val weatherResult = weatherRepository.getCurrentWeather(DEFAULT_CITY)

            weatherResult.onSuccess { weather ->
                val category = MealSuggestionEngine.suggestCategory(
                    conditionCode = weather.conditionCode,
                    temperatureCelsius = weather.temperatureCelsius
                )
                val reason = MealSuggestionEngine.suggestReason(
                    conditionCode = weather.conditionCode,
                    temperatureCelsius = weather.temperatureCelsius
                )

                val mealResult = mealRepository.filterMealsByCategory(category)
                val suggestedMeal = mealResult.getOrNull()?.randomOrNull()

                _uiState.value = _uiState.value.copy(
                    weather = weather,
                    suggestedMeal = suggestedMeal,
                    suggestionReason = reason,
                    isWeatherLoading = false
                )
            }.onFailure {
                // Silent fail — hide the weather card, keep the app working.
                _uiState.value = _uiState.value.copy(
                    weather = null,
                    suggestedMeal = null,
                    suggestionReason = null,
                    isWeatherLoading = false
                )
            }
        }
    }

    fun retryWeather() {
        loadWeatherAndSuggestion()
    }

    companion object {
        private const val DEFAULT_CITY = "Alexandria"
    }
}
