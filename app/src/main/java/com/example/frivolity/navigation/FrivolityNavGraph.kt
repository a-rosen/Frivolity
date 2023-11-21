package com.example.frivolity.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            )
        }
    }
}
