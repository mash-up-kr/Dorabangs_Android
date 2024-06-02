package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mashup.dorabangs.feature.home.HomeRoute

private enum class Screen(val route: String) {
    Home("home")
}

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeRoute()
        }
    }
}