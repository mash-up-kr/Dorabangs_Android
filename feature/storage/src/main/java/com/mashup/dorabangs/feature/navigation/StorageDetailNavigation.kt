package com.mashup.dorabangs.feature.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.core.navigation.bundleSerializable
import com.mashup.core.navigation.serializableNavType
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.StorageDetailRoute
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavController.navigateToStorageDetail(folder: Folder) {
    val folderItem = Uri.encode(Json.encodeToString(folder))
    navigate("${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folderItem=$folderItem")
}

fun NavGraphBuilder.storageDetailNavigation(
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToCreateFolder: () -> Unit,
    navigateToFolderManager: (String) -> Unit,
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folderItem={folder}",
        arguments = listOf(
            navArgument("folder") {
                type = serializableNavType<Folder>()
            },
        ),
    ) { navBackStackEntry ->
        val folderItem = navBackStackEntry.arguments?.bundleSerializable("folder") as Folder?
        val editFolderName = navBackStackEntry.savedStateHandle.get<String>("editFolderName").orEmpty()
        val isVisibleBottomSheet = navBackStackEntry.savedStateHandle.get<Boolean>("isVisibleBottomSheet") ?: false

        folderItem?.let { item ->
            StorageDetailRoute(
                folderItem = if (editFolderName.isNotEmpty()) folderItem.copy(name = editFolderName) else item,
                isVisibleBottomSheet = isVisibleBottomSheet,
                onClickBackIcon = onClickBackIcon,
                navigateToHome = navigateToHome,
                navigateToFolderManager = navigateToFolderManager,
                navigateToCreateFolder = navigateToCreateFolder,

            )
        }
    }
}
