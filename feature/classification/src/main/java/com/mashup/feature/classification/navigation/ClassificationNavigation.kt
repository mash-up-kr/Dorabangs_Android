package com.mashup.feature.classification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.feature.classification.ClassificationRoute

fun NavController.navigateToClassification() = navigate(NavigationRoute.ClassificationScreen.route)

fun NavGraphBuilder.classificationNavigation(navController: NavController) {
    composable(
        route = NavigationRoute.ClassificationScreen.route,
    ) {
        ClassificationRoute(
            onClickBackIcon = { navController.popBackStack() },
        )
    }
}
