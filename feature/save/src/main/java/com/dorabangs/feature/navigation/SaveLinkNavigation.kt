package com.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dorabangs.feature.save.DoraLinkSaveRoute
import com.mashup.core.navigation.NavigationRoute

fun NavController.navigateToSaveLink(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.SaveLink.route, navOptions)

fun NavGraphBuilder.saveLinkNavigation(navController: NavHostController) {
    composable(
        route = NavigationRoute.SaveLink.route,
    ) {
        DoraLinkSaveRoute(onClickBackIcon = {
            navController.navigate(NavigationRoute.SaveLink.route)
        })
    }
}
