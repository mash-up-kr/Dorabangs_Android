package com.mashup.core.navigation

sealed class NavigationRoute(val route: String) {
    object OnBoardingScreen : NavigationRoute("onboarding")
    object HomeScreen : NavigationRoute("home")
    object StorageScreen : NavigationRoute("storage") {
        object StorageDetailScreen : NavigationRoute("storage/detail")
    }
    object ClassificationScreen : NavigationRoute("classification")
    object SaveLink : NavigationRoute("save")
}
