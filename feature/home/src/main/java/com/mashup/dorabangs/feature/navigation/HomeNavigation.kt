package com.mashup.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null, isVisibleMovingBottomSheet: Boolean = false, folderRemoveSuccess: Boolean = false) =
    navigate("${NavigationRoute.HomeScreen.route}/$isVisibleMovingBottomSheet/$folderRemoveSuccess", navOptions)

fun NavGraphBuilder.homeNavigation(
    navigateToClassification: () -> Unit,
    navigateToSaveScreenWithLink: (String) -> Unit,
    navigateToSaveScreenWithoutLink: () -> Unit,
    navigateToCreateFolder: () -> Unit,
    navigateToHomeTutorial: () -> Unit,
    navigateToWebView: (String) -> Unit,
    navigateToUnreadStorageDetail: (Folder) -> Unit,
) {
    composable(
        route = "${NavigationRoute.HomeScreen.route}/{isVisibleMovingBottomSheet}/{folderRemoveSuccess}",
        arguments = listOf(
            navArgument(name = "isVisibleMovingBottomSheet") {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
    ) {
        HomeRoute(
            navigateToClassification = navigateToClassification,
            navigateToSaveScreenWithLink = navigateToSaveScreenWithLink,
            navigateToSaveScreenWithoutLink = navigateToSaveScreenWithoutLink,
            navigateToCreateFolder = navigateToCreateFolder,
            navigateToHomeTutorial = navigateToHomeTutorial,
            navigateToWebView = navigateToWebView,
            navigateToUnreadStorageDetail = navigateToUnreadStorageDetail,
        )
    }
}
