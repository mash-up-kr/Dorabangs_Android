package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.core.navigation.bundleSerializable
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.StorageDetailRoute
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType

fun NavController.navigateToStorageDetail(folderId: String) {
    navigate("${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folderId=$folderId")
}

fun NavGraphBuilder.storageDetailNavigation(
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToFolderManager: (String, EditActionType) -> Unit,
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folderId={folderId}",
        arguments = listOf(
            navArgument("folderId") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) { navBackStackEntry ->
        val isVisibleBottomSheet = navBackStackEntry.savedStateHandle.get<Boolean>("isVisibleBottomSheet") ?: false
        val isChangeData = navBackStackEntry.savedStateHandle.get<Boolean>("isChange") ?: false

        StorageDetailRoute(
            isVisibleBottomSheet = isVisibleBottomSheet,
            isChangeData = isChangeData,
            onClickBackIcon = onClickBackIcon,
            navigateToHome = navigateToHome,
            navigateToFolderManager = navigateToFolderManager,
        )
    }
}
