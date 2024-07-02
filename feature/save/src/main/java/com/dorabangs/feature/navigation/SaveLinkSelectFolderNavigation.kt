package com.dorabangs.feature.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dorabangs.feature.save.DoraLinkSaveSelectFolderRoute
import com.mashup.core.navigation.NavigationRoute

fun NavController.navigateToSaveLinkSelectFolder(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.SaveLink.SelectFolder.route, navOptions)

fun NavGraphBuilder.saveLinkSelectFolder(
    navController: NavHostController,
    onClickSaveButton: () -> Unit,
) {
    composable(
        route = NavigationRoute.SaveLink.SelectFolder.route,
    ) {
        DoraLinkSaveSelectFolderRoute(
            modifier = Modifier,
            onClickSaveButton = onClickSaveButton,
            onClickBackIcon = {
                navController.popBackStack()
            },
        )
    }
}
