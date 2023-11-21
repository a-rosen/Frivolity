package com.example.frivolity.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.frivolity.navigation.FrivolityNavGraph

@Composable
fun AppContainerScreen(navController: NavHostController = rememberNavController()) {
    FrivolityNavGraph(navController = navController)
}