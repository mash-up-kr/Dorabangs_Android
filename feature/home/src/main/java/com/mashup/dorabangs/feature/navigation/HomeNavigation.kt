package com.mashup.dorabangs.feature.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.home.HomeRoute
import com.mashup.dorabangs.feature.home.HomeViewModel

fun NavController.navigateToHome(navOptions: NavOptions? = null, isVisibleMovingBottomSheet: Boolean = false) =
    navigate("${NavigationRoute.HomeScreen.route}/$isVisibleMovingBottomSheet", navOptions)

fun NavGraphBuilder.homeNavigation(
    navigateToClassification: () -> Unit,
    navigateToSaveScreenWithLink: (String) -> Unit,
    navigateToSaveScreenWithoutLink: () -> Unit,
    navigateToCreateFolder: () -> Unit,
) {
    composable(
        route = "${NavigationRoute.HomeScreen.route}/{isVisibleMovingBottomSheet}",
        arguments = listOf(
            navArgument(name = "isVisibleMovingBottomSheet") {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
    ) { backStackEntry ->

        val homeViewModel: HomeViewModel = hiltViewModel()
        Log.d(TAG, "homeNavigationbackStackEntry: ${backStackEntry.savedStateHandle.get<Boolean>("isVisibleMovingBottomSheet")}")
        HomeRoute(
            viewModel = homeViewModel,
            navigateToClassification = navigateToClassification,
            navigateToSaveScreenWithLink = navigateToSaveScreenWithLink,
            navigateToSaveScreenWithoutLink = navigateToSaveScreenWithoutLink,
            navigateToCreateFolder = navigateToCreateFolder,
        )
    }
}
