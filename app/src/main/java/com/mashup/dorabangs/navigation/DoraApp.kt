package com.mashup.dorabangs.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.BottomNavigationDestination
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.BottomNavigationItems
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.DoraBottomNavigation
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

@Composable
fun DoraApp(appState: DoraAppState = rememberDoraAppState()) {
    Scaffold(
        bottomBar = {
            DoraBottomBar(
                destinations = appState.bottomBarDestination,
                onNavigateToDestination = appState::navigateToBottomNavigationDestination,
                currentDestination = appState.currentDestination,
            )
        },
        content = { paddingValues: PaddingValues ->
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                MainNavHost(
                    appState = appState,
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
            val selected = currentDestination.isBottomDestinationInHierarchy(destination)
            BottomNavigationItems(
                modifier = modifier,
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = null,
                    )
                },
                label = destination.routeName,
                contentColor = Color.Transparent,
                selectedColor = DoraColorTokens.G9,
                unSelectedColor = DoraColorTokens.G2,
            )
        }
    }
}

private fun NavDestination?.isBottomDestinationInHierarchy(destination: BottomNavigationDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
