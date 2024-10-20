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
    navigateToStorageDetail: (Folder) -> Unit,
    navigateToFolderManage: (FolderManageType, String) -> Unit,

) {
    composable(
        route = NavigationRoute.StorageScreen.route,
    ) { navBackStackEntry ->
        val isChangedData = navBackStackEntry.savedStateHandle.get<Boolean>("isChanged") ?: false
        val isRemoveSuccess = navBackStackEntry.savedStateHandle.get<Boolean>("isRemoveSuccess") ?: false

        StorageRoute(
            isChangedData = isChangedData,
            isRemoveSuccess = isRemoveSuccess,
            navigateToStorageDetail = navigateToStorageDetail,
            navigateToFolderManage = navigateToFolderManage,
        )
    }
}
