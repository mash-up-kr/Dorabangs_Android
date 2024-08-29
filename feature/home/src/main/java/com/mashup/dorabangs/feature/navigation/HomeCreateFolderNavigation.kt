package com.mashup.dorabangs.feature.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.createfolder.HomeCreateFolderRoute
import com.mashup.dorabangs.feature.home.HomeViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToHomeCrateFolder(navOptions: NavOptions? = null, urlLink: String = "") {
    val encodedUrl = URLEncoder.encode(urlLink, StandardCharsets.UTF_8.toString())
    navigate("${NavigationRoute.HomeScreen.HomeCreateFolderWithLink.route}/?$encodedUrl", navOptions)
}

fun NavGraphBuilder.homeCreateFolderNavigation(
    navController: NavController,
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToHomeAfterSaveLink: () -> Unit,
    navigateToHomeAfterMovingFolder: () -> Unit,
) {
    composable(
        route = "${NavigationRoute.HomeScreen.HomeCreateFolderWithLink.route}/?{urlLink}",
        arguments = listOf(
            navArgument("urlLink") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) {
        navController.previousBackStackEntry?.let { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            val urlLink = it.arguments?.getString("urlLink").orEmpty()
            homeViewModel.savedStateHandle["urlLink"] = urlLink
            homeViewModel.savedStateHandle["isVisibleMovingBottomSheet"] = urlLink.isEmpty()

            HomeCreateFolderRoute(
                homeViewModel = homeViewModel,
                onClickBackIcon = onClickBackIcon,
                navigateToHome = navigateToHome,
                navigateToHomeAfterSaveLink = navigateToHomeAfterSaveLink,
                navigateToHomeAfterMovingFolder = navigateToHomeAfterMovingFolder,
            )
        } ?: HomeCreateFolderRoute(
            onClickBackIcon = onClickBackIcon,
            navigateToHome = navigateToHome,
            navigateToHomeAfterSaveLink = navigateToHomeAfterSaveLink,
            navigateToHomeAfterMovingFolder = navigateToHomeAfterMovingFolder,
        )
    }
}
