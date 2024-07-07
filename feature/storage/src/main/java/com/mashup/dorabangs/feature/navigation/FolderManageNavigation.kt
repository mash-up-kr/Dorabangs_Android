package com.mashup.dorabangs.feature.navigation

import android.icu.text.CaseMap.Fold
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.folders.StorageFolderManageRoute
import com.mashup.dorabangs.feature.folders.model.FolderManageType

fun NavController.navigateToStorageFolderManage(
    folderManageType: FolderManageType,
    navOptions: NavOptions? = null) = navigate("${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/${folderManageType.name}", navOptions)

fun NavGraphBuilder.storageFolderManageNavigation(
    onClickBackIcon: () -> Unit = {},
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/{folderManageType}",
        arguments = listOf(
            navArgument("folderManageType") {
                type = NavType.StringType
                defaultValue = FolderManageType.CHANGE.name
            }
        )
    ) { navBackStackEntry ->
        val folderManageType = navBackStackEntry.arguments?.getString("folderManageType") ?: FolderManageType.CHANGE.name
        StorageFolderManageRoute(
            folderManageType = folderManageType,
            onClickBackIcon = onClickBackIcon,
        )
    }
}
