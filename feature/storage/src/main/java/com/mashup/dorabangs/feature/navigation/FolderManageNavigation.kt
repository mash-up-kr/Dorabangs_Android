package com.mashup.dorabangs.feature.navigation

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
    folderId: String,
    navOptions: NavOptions? = null,
) = navigate("${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/${folderManageType.name}/$folderId", navOptions)

fun NavGraphBuilder.storageFolderManageNavigation(
    onClickBackIcon: () -> Unit = {},
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/{folderManageType}/{folderId}",
        arguments = listOf(
            navArgument("folderManageType") {
                type = NavType.StringType
                defaultValue = FolderManageType.CHANGE.name
            },
            navArgument("folderId") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) { navBackStackEntry ->
        val folderManageType = navBackStackEntry.arguments?.getString("folderManageType") ?: FolderManageType.CHANGE.name
        val folderId = navBackStackEntry.arguments?.getString("folderId").orEmpty()
        StorageFolderManageRoute(
            folderId = folderId,
            folderManageType = folderManageType,
            onClickBackIcon = onClickBackIcon,
        )
    }
}
