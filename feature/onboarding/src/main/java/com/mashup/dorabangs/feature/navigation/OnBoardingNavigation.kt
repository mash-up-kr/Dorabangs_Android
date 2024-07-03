package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.onboarding.OnBoardingRoute

fun NavController.navigateToOnBoarding(navOptions: NavOptions) =
    navigate(NavigationRoute.OnBoardingScreen.route, navOptions)

fun NavGraphBuilder.onBoardingNavigation(navigateToHome: () -> Unit) {
    composable(
        route = NavigationRoute.OnBoardingScreen.route,
    ) {
        OnBoardingRoute(
            navigateToHome = navigateToHome,
        )
    }
}
