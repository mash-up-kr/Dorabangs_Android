package com.mashup.dorabangs.feature.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.createfolder.HomeCreateFolderRoute
import com.mashup.dorabangs.feature.home.HomeViewModel

fun NavController.navigateToHomeCrateFolder(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.HomeCreateFolder.route, navOptions)

fun NavGraphBuilder.homeCreateFolderNavigation(
    navController: NavController,
    onClickBackIcon: () -> Unit,
) {
    composable(
        route = NavigationRoute.HomeScreen.HomeCreateFolder.route,
    ) {
        navController.previousBackStackEntry?.let { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            homeViewModel.savedStateHandle["isVisibleMovingBottomSheet"] = true

            HomeCreateFolderRoute(
                homeViewModel = homeViewModel,
                onClickBackIcon = onClickBackIcon,
            )
        } ?: HomeCreateFolderRoute(
            onClickBackIcon = onClickBackIcon,
        )
    }
}
