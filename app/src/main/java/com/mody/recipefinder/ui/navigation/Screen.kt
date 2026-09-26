package com.mody.recipefinder.ui.navigation

object Routes {
    const val HOME = "home"
    const val RESULTS = "results"
    const val DETAIL = "detail/{mealId}"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val ASSISTANT = "assistant"

    fun detailRoute(mealId: String): String = "detail/$mealId"
}
