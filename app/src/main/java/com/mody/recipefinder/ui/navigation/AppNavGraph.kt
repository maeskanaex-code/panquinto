package com.mody.recipefinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mody.recipefinder.data.repository.MealRepository
import com.mody.recipefinder.ui.RecipeFinderViewModelFactory
import com.mody.recipefinder.ui.detail.DetailScreen
import com.mody.recipefinder.ui.detail.DetailViewModel
import com.mody.recipefinder.ui.favorites.FavoritesScreen
import com.mody.recipefinder.ui.favorites.FavoritesViewModel
import com.mody.recipefinder.ui.home.HomeScreen
import com.mody.recipefinder.ui.home.HomeViewModel

/**
 * The whole navigation graph.
 *
 * Three destinations: Home (search + categories + results grid),
 * Detail (one recipe), Favorites (Room-backed list).
 *
 * Results are shown inside Home — there is no separate Results screen
 * in this MVP. If the grid feels cramped, we can add one later.
 */
@Composable
fun AppNavGraph(
    repository: MealRepository,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository)
            )
            HomeScreen(
                viewModel = vm,
                onMealClick = { mealId ->
                    navController.navigate(Routes.detailRoute(mealId))
                },
                onFavoritesClick = {
                    navController.navigate(Routes.FAVORITES)
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId").orEmpty()
            val vm: DetailViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository)
            )
            LaunchedEffect(mealId) {
                if (mealId.isNotEmpty()) vm.loadMeal(mealId)
            }
            DetailScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FAVORITES) {
            val vm: FavoritesViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository)
            )
            FavoritesScreen(
                viewModel = vm,
                onMealClick = { mealId ->
                    navController.navigate(Routes.detailRoute(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
