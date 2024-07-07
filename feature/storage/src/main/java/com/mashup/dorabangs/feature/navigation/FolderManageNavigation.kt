package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.folders.StorageFolderManageRoute

fun NavController.navigateToStorageFolderManage(navOptions: NavOptions? = null) = navigate(NavigationRoute.StorageScreen.StorageFolderManageScreen.route, navOptions)

fun NavGraphBuilder.storageFolderManageNavigation(
    onClickBackIcon: () -> Unit = {},
) {
    composable(
        route = NavigationRoute.StorageScreen.StorageFolderManageScreen.route,
    ) {
        StorageFolderManageRoute(
            onClickBackIcon = onClickBackIcon,
        )
    }
}
