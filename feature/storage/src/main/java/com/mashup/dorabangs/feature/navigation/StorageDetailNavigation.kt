package com.mashup.dorabangs.feature.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.core.navigation.bundleSerializable
import com.mashup.core.navigation.serializableNavType
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.StorageDetailRoute
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavController.navigateToStorageDetail(folder: Folder) {
    val folderItem = Uri.encode(Json.encodeToString(folder))
    navigate("${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folder=$folderItem?")
}

fun NavController.navigateToUnreadStorageDetail(folder: Folder) {
    val folderItem = Uri.encode(Json.encodeToString(folder))
    navigate("${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folder=$folderItem?unread=true")
}

fun NavGraphBuilder.storageDetailNavigation(
    onClickBackIcon: (Boolean) -> Unit,
    navigateToWebView: (FeedUiModel.FeedCardUiModel) -> Unit,
    navigateToStorage: (Boolean) -> Unit,
    navigateToFolderManager: (String, EditActionType) -> Unit,
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageDetailScreen.route}/folder={folder}?unread={unread}",
        arguments = listOf(
            navArgument("folder") {
                type = serializableNavType<Folder>()
            },
            navArgument("unread") {
                defaultValue = false
            },
        ),
    ) { navBackStackEntry ->
        val folderItem = navBackStackEntry.arguments?.bundleSerializable("folder") as Folder?
        val isUnread = navBackStackEntry.arguments?.bundleSerializable("unread") as? Boolean ?: false
        val isVisibleBottomSheet = navBackStackEntry.savedStateHandle.get<Boolean>("isVisibleBottomSheet") ?: false
        val isChangedData = navBackStackEntry.savedStateHandle.get<Boolean>("isChanged") ?: false
        val changeFolderName = navBackStackEntry.savedStateHandle.get<String>("folderName").orEmpty()

        folderItem?.let { item ->
            StorageDetailRoute(
                folderItem = item,
                isUnread = isUnread,
                isVisibleBottomSheet = isVisibleBottomSheet,
                isChangedData = isChangedData,
                changeFolderName = changeFolderName,
                onClickBackIcon = onClickBackIcon,
                navigateToStorage = navigateToStorage,
                navigateToFolderManager = navigateToFolderManager,
                navigateToWebView = navigateToWebView,
            )
        }
    }
}
