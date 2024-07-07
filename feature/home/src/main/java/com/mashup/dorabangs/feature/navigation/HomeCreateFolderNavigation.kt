package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.createfolder.HomeCreateFolderRoute

fun NavController.navigateToHomeCrateFolder(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.HomeCreateFolder.route, navOptions)

fun NavGraphBuilder.homeCreateFolderNavigation(
    onClickBackIcon: () -> Unit,
) {
    composable(
        route = NavigationRoute.HomeScreen.HomeCreateFolder.route,
    ) {
        HomeCreateFolderRoute(
            onClickBackIcon = onClickBackIcon,
        )
    }
}
