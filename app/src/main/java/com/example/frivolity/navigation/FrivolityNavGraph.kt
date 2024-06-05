package com.example.frivolity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.frivolity.ui.screens.DetailsDestination
import com.example.frivolity.ui.screens.ItemDetailScreen
import com.example.frivolity.ui.screens.ItemDetailScreenState
import com.example.frivolity.ui.screens.ItemDetailScreenViewModel
import com.example.frivolity.ui.screens.MainScreen
import com.example.frivolity.ui.screens.MainScreenViewModel
import com.example.frivolity.ui.screens.SettingsScreen


@Composable
fun FrivolityNavGraph(
    navController: NavHostController,
    startDestination: String = "SettingsScreen"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("SettingsScreen") {
            SettingsScreen(
                viewModel = hiltViewModel(),
                navigateToMainScreen = {
                    navController.navigate(
                        route = "MainScreen"
                    )
                },
            )
        }

        composable("MainScreen") {
            val viewModel = hiltViewModel<MainScreenViewModel>()
            val state = viewModel.screenStateFlow.collectAsState()
            MainScreen(
                state = state.value,
                updateSearchBoxText = viewModel::updateSearchBoxText,
                submitSearch = viewModel::submitSearch,
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
            val itemDetailScreenState by hiltViewModel<ItemDetailScreenViewModel>().screenStateFlow.collectAsState(
                initial = ItemDetailScreenState.EMPTY
            )
            ItemDetailScreen(
                state = itemDetailScreenState,
                viewModel = hiltViewModel(),
                navigateBack = {
                    navController.navigate(
                        route = "MainScreen"
                    )
                },
                changeServer = { worldname, id ->
                    navController.navigate(
                        "${DetailsDestination.route}/$worldname/$id"
                    )
                }
            )
        }
    }
}
