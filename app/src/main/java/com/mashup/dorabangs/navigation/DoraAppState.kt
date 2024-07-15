package com.mashup.dorabangs.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.core.designsystem.component.bottomnavigation.BottomNavigationDestination
import com.mashup.dorabangs.feature.navigation.navigateToHome
import com.mashup.dorabangs.feature.navigation.navigateToStorage

@Composable
fun rememberDoraAppState(navController: NavHostController = rememberNavController()): DoraAppState {
    return remember(navController) {
        DoraAppState(
            navController,
        )
    }
}

@Stable
class DoraAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigateToBottomNavigationDestination(bottomNavigationDestination: BottomNavigationDestination) {
        val bottomNavigationOption =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

        when (bottomNavigationDestination) {
            BottomNavigationDestination.HOME -> navController.navigateToHome(bottomNavigationOption)
            BottomNavigationDestination.STORAGE -> navController.navigateToStorage(bottomNavigationOption)
        }
    }

    @Composable
    fun isBottomBarVisible(): Boolean {
        val homeRoute = "${NavigationRoute.HomeScreen.route}/{isVisibleMovingBottomSheet}"
        return when (currentDestination?.route) {
            homeRoute, NavigationRoute.StorageScreen.route -> true
            else -> false
        }
    }

    val bottomBarDestination: List<BottomNavigationDestination> = BottomNavigationDestination.values().toList()
}
