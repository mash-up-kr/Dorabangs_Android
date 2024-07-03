package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.dorabangs.feature.navigation.navigateToSaveLinkSelectFolder
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
            appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = copiedUrl)
        }
        storageNavigation(navController = appState.navController)
        storageDetailNavigation(navController = appState.navController)
        saveLinkNavigation(
            onClickBackIcon = { appState.navController.popBackStack() },
            onClickSaveButton = { appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = "") }
        )
        saveLinkSelectFolder(
            onClickBackButton = {
                appState.navController.popBackStack()
            },
            onClickSaveButton = {
                // TODO 다하고 저장누르면 서버에 정보 날리고 홈으로 이동
                // TODO 클릭 때 데이터 스토어에 저장해서 다시 클립보드 안뜨게 하기
                appState.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(appState.navController.graph.id) {
                            inclusive = true
                        }
                    },
                )
            },
        )
    }
}
