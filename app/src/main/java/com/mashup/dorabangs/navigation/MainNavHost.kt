package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.dorabangs.feature.navigation.navigateToSaveLink
import com.dorabangs.feature.navigation.saveLinkNavigation
import com.dorabangs.feature.navigation.saveLinkSelectFolder
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.navigation.homeNavigation
import com.mashup.dorabangs.feature.navigation.navigateToHome
import com.mashup.dorabangs.feature.storage.navigation.storageDetailNavigation
import com.mashup.dorabangs.feature.storage.navigation.storageNavigation

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: DoraAppState,
    startDestination: String = NavigationRoute.HomeScreen.route,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        homeNavigation { copiedUrl ->
            appState.navController.navigateToSaveLink(copiedUrl = copiedUrl)
        }
        storageNavigation(navController = appState.navController)
        storageDetailNavigation(navController = appState.navController)
        saveLinkNavigation(navController = appState.navController)
        saveLinkSelectFolder(navController = appState.navController) {
            // TODO 다하고 저장누르면 서버에 정보 날리고 홈으로 이동
            val bottomNavigationOption =
                navOptions {
                    popUpTo(appState.navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            appState.navController.navigateToHome(navOptions = bottomNavigationOption)
        }
    }
}
