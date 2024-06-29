package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.navigation.homeNavigation
import com.mashup.dorabangs.feature.storage.navigation.storageNavigation

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: DoraAppState,
    startDestination: String = NavigationRoute.HomeScreen.route,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        homeNavigation()
        storageNavigation()
    }
}
