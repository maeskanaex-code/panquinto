package com.mody.recipefinder.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.domain.QueryIntent
import com.mody.recipefinder.domain.QueryParser
import com.mody.recipefinder.domain.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AssistantUiState(
    val query: String = "",
    val results: List<Meal> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val reason: String? = null,
    val isLoading: Boolean = false,
    val notUnderstood: Boolean = false,
    val errorMessage: String? = null
)

class AssistantViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AssistantUiState())
    val uiState: StateFlow<AssistantUiState> = _uiState.asStateFlow()

    init {
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
        _uiState.value = _uiState.value.copy(
            query = newQuery,
            notUnderstood = false,
            errorMessage = null
        )
    }

    fun ask(prompt: String = _uiState.value.query) {
        val trimmed = prompt.trim()
        if (trimmed.isBlank()) return

        val intent = QueryParser.parse(trimmed)

        if (!intent.isUnderstood) {
            _uiState.value = _uiState.value.copy(
                query = trimmed,
                notUnderstood = true,
                results = emptyList(),
                reason = null,
                isLoading = false
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            query = trimmed,
            isLoading = true,
            notUnderstood = false,
            errorMessage = null
        )

        viewModelScope.launch {
            val result = executeIntent(intent)
            result
                .onSuccess { meals ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        results = meals,
                        reason = intent.reason,
                        notUnderstood = meals.isEmpty()
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Couldn't reach the recipe service"
                    )
                }
        }
    }

    private suspend fun executeIntent(intent: QueryIntent): Result<List<Meal>> {
        return when {
            intent.ingredient != null ->
                repository.filterMealsByIngredient(intent.ingredient)

            intent.category != null ->
                repository.filterMealsByCategory(intent.category)

            intent.keywords.isNotEmpty() ->
                repository.searchMealsByName(intent.keywords.joinToString(" "))

            else -> Result.success(emptyList())
        }
    }

    fun clearNotUnderstood() {
        _uiState.value = _uiState.value.copy(notUnderstood = false)
    }
}
