package com.mashup.dorabangs.feature.storage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.storage.storagedetail.StorageDetailRoute

fun NavController.navigateToStorageDetail() = navigate(NavigationRoute.StorageScreen.StorageDetailScreen.route)

fun NavGraphBuilder.storageDetailNavigation(navController: NavController) {
    composable(
        route = NavigationRoute.StorageScreen.StorageDetailScreen.route,
    ) {
        StorageDetailRoute(
            onClickBackIcon = {
                navController.popBackStack()
            },
        )
    }
}
