package com.dorabangs.feature.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dorabangs.feature.save.screen.DoraLinkSaveSelectFolderRoute
import com.mashup.core.navigation.NavigationRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToSaveLinkSelectFolder(
    copiedUrl: String,
    navOptions: NavOptions? = null,
) {
    val encodedUrl = URLEncoder.encode(copiedUrl, StandardCharsets.UTF_8.toString())
    navigate("${NavigationRoute.SaveLink.SelectFolder.route}/$encodedUrl", navOptions)
}

fun NavGraphBuilder.saveLinkSelectFolder(
    onClickBackButton: () -> Unit,
    onClickSaveButton: () -> Unit,
) {
    composable(
        route = "${NavigationRoute.SaveLink.SelectFolder.route}/{copiedUrl}",
        arguments = listOf(
            navArgument("copiedUrl") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) {
        DoraLinkSaveSelectFolderRoute(
            modifier = Modifier,
            onClickSaveButton = onClickSaveButton,
            onClickBackIcon = onClickBackButton,
        )
    }
}
