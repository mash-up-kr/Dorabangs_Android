package com.mashup.dorabangs.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.BottomNavigationDestination
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.BottomNavigationItems
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.DoraBottomNavigation

@Composable
fun DoraApp(
    isFirstEntry: Boolean,
    appState: DoraAppState = rememberDoraAppState(),
) {
    Scaffold(
        bottomBar = {
            if (appState.isBottomBarVisible()) {
                DoraBottomBar(
                    destinations = appState.bottomBarDestination,
                    onNavigateToDestination = appState::navigateToBottomNavigationDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                MainNavHost(
                    appState = appState,
                    startDestination =
                    if (isFirstEntry) NavigationRoute.OnBoardingScreen.route else "${NavigationRoute.HomeScreen.route}/false"
                )
            }
        },
    )
}

@Composable
private fun DoraBottomBar(
    destinations: List<BottomNavigationDestination>,
    onNavigateToDestination: (BottomNavigationDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    DoraBottomNavigation {
        destinations.forEach { destination ->
            val isSelected = currentDestination.isSelectedBottomNaviPage(destination)
            BottomNavigationItems(
                modifier = modifier,
                selected = isSelected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = null,
                    )
                },
                label = destination.routeName,
            )
        }
    }
}

private fun NavDestination?.isSelectedBottomNaviPage(destination: BottomNavigationDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
