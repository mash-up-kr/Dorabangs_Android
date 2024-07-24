package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.tutorial.HomeTutorialRoute

fun NavController.navigateToHomeTutorial(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.HomeTutorial.route, navOptions)

fun NavGraphBuilder.homeTutorialNavigation(
    onClickBackIcon: () -> Unit,
) {
    composable(
        route = NavigationRoute.HomeScreen.HomeTutorial.route,
    ) {
        HomeTutorialRoute(
            onClickBackIcon = onClickBackIcon,
        )
    }
}
