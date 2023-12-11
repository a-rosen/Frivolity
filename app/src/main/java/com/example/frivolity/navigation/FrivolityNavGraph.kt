package com.example.frivolity.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.frivolity.ui.screens.DetailsDestination
import com.example.frivolity.ui.screens.ItemDetailScreen
import com.example.frivolity.ui.screens.MainScreen


@Composable
fun FrivolityNavGraph(
    navController: NavHostController,
    startDestination: String = "MainScreen"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("MainScreen") {
            MainScreen(
                viewModel = hiltViewModel(),
                navigateToDetailScreen = { worldname, id ->
                    navController.navigate(
                        "${DetailsDestination.route}/$worldname/$id"
                    )
                }
            )
        }
        composable(
            route = DetailsDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailsDestination.itemIdArg)
                { type = NavType.IntType }
            )
        ) {
            ItemDetailScreen(
                viewModel = hiltViewModel(),
            )
        }
    }
}
