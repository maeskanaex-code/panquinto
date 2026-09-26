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
import com.mody.recipefinder.data.repository.WeatherRepository
import com.mody.recipefinder.ui.RecipeFinderViewModelFactory
import com.mody.recipefinder.ui.assistant.AssistantScreen
import com.mody.recipefinder.ui.assistant.AssistantViewModel
import com.mody.recipefinder.ui.detail.DetailScreen
import com.mody.recipefinder.ui.detail.DetailViewModel
import com.mody.recipefinder.ui.favorites.FavoritesScreen
import com.mody.recipefinder.ui.favorites.FavoritesViewModel
import com.mody.recipefinder.ui.home.HomeScreen
import com.mody.recipefinder.ui.home.HomeViewModel
import com.mody.recipefinder.ui.settings.SettingsScreen

@Composable
fun AppNavGraph(
    repository: MealRepository,
    weatherRepository: WeatherRepository,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository, weatherRepository)
            )
            HomeScreen(
                viewModel = vm,
                onMealClick = { mealId ->
                    navController.navigate(Routes.detailRoute(mealId))
                },
                onFavoritesClick = {
                    navController.navigate(Routes.FAVORITES)
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                },
                onAssistantClick = {
                    navController.navigate(Routes.ASSISTANT)
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId").orEmpty()
            val vm: DetailViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository, weatherRepository)
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
                factory = RecipeFinderViewModelFactory(repository, weatherRepository)
            )
            FavoritesScreen(
                viewModel = vm,
                onMealClick = { mealId ->
                    navController.navigate(Routes.detailRoute(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ASSISTANT) {
            val vm: AssistantViewModel = viewModel(
                factory = RecipeFinderViewModelFactory(repository, weatherRepository)
            )
            AssistantScreen(
                viewModel = vm,
                onMealClick = { mealId ->
                    navController.navigate(Routes.detailRoute(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
