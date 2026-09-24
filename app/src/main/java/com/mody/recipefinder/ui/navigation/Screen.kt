package com.mody.recipefinder.ui.navigation

/**
 * Type-safe route definitions.
 *
 * We use plain string routes with argument placeholders ({mealId}).
 * Navigation Compose resolves them at runtime.
 *
 * Alternative: the newer type-safe nav with @Serializable objects.
 * That's cleaner but requires kotlinx-serialization, extra setup, and
 * a different API that trips up reviewers on older codebases.
 * Plain string routes are the widely-understood default.
 */
object Routes {
    const val HOME = "home"
    const val RESULTS = "results"
    const val DETAIL = "detail/{mealId}"
    const val FAVORITES = "favorites"

    fun detailRoute(mealId: String): String = "detail/$mealId"
}
