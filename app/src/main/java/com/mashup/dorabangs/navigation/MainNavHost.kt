package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.navigation.homeNavigation
import com.mashup.dorabangs.feature.storage.navigation.storageDetailNavigation
import com.mashup.dorabangs.feature.storage.navigation.storageNavigation
import com.mashup.feature.classification.navigation.classificationNavigation
import com.mashup.feature.classification.navigation.navigateToClassification

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
        homeNavigation(
            navigateToClassification = { appState.navController.navigateToClassification() },
        )
        storageNavigation(appState.navController)
        storageDetailNavigation(appState.navController)
        classificationNavigation(appState.navController)
    }
}
