package com.mashup.dorabangs.feature.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.core.navigation.bundleSerializable
import com.mashup.core.navigation.serializableNavType
import com.mashup.dorabangs.feature.folders.StorageFolderManageRoute
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavController.navigateToStorageFolderManage(
    folderManageType: FolderManageType,
    itemId: String? = null,
    actionType: EditActionType? = null,
    navOptions: NavOptions? = null,
) {
    val editType = Uri.encode(Json.encodeToString(actionType))
    return navigate("${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/${folderManageType.name}?itemId=$itemId/$editType", navOptions)
}

fun NavGraphBuilder.storageFolderManageNavigation(
    onClickBackIcon: (FolderManageType) -> Unit = {},
    navigateToComplete: (String) -> Unit,
) {
    composable(
        route = "${NavigationRoute.StorageScreen.StorageFolderManageScreen.route}/{folderManageType}?itemId={itemId}/{editType}",
        arguments = listOf(
            navArgument("folderManageType") {
                type = NavType.StringType
                defaultValue = FolderManageType.CHANGE.name
            },
            navArgument("itemId") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("editType") {
                type = serializableNavType<EditActionType>()
            },
        ),
    ) { navBackStackEntry ->
        val folderManageType = navBackStackEntry.arguments?.getString("folderManageType") ?: FolderManageType.CHANGE.name
        val itemId = navBackStackEntry.arguments?.getString("itemId").orEmpty()
        val editType = navBackStackEntry.arguments?.bundleSerializable("editType") as EditActionType?

        StorageFolderManageRoute(
            itemId = itemId,
            editType = editType,
            folderManageType = folderManageType,
            onClickBackIcon = onClickBackIcon,
            navigateToComplete = navigateToComplete,
        )
    }
}
