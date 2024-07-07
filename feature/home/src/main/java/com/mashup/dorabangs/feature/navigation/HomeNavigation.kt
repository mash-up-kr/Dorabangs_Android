package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.route, navOptions)

fun NavGraphBuilder.homeNavigation(
    navigateToClassification: () -> Unit = {},
    navigateToSaveScreenWithLink: (String) -> Unit,
    navigateToSaveScreenWithoutLink: () -> Unit,
) {
    composable(
        route = NavigationRoute.HomeScreen.route,
    ) {
        HomeRoute(
            navigateToClassification = navigateToClassification,
            navigateToSaveScreenWithLink = navigateToSaveScreenWithLink,
            navigateToSaveScreenWithoutLink = navigateToSaveScreenWithoutLink,
        )
    }
}
