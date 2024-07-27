package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.storage.storage.StorageRoute

fun NavController.navigateToStorage(navOptions: NavOptions) = navigate(NavigationRoute.StorageScreen.route, navOptions)

fun NavGraphBuilder.storageNavigation(
    navigateToStorageDetail: (String) -> Unit,
    navigateToFolderManage: (FolderManageType, String) -> Unit,

) {
    composable(
        route = NavigationRoute.StorageScreen.route,
    ) { navBackStackEntry ->
        val isChangeData = navBackStackEntry.savedStateHandle.get<Boolean>("isChange") ?: false

        StorageRoute(
            isChangeData = isChangeData,
            navigateToStorageDetail = navigateToStorageDetail,
            navigateToFolderManage = navigateToFolderManage,
        )
    }
}
