package com.mashup.core.navigation

sealed class NavigationRoute(val route: String) {
    object OnBoardingScreen : NavigationRoute("onboarding")
    object HomeScreen : NavigationRoute("home") {
        object HomeCreateFolder : NavigationRoute("home/createfolder")
    }
    object StorageScreen : NavigationRoute("storage") {
        object StorageDetailScreen : NavigationRoute("storage/detail")
    }
    object ClassificationScreen : NavigationRoute("classification")
    object SaveLink : NavigationRoute("save/copiedUrl") {
        object SelectFolder : NavigationRoute("save/folder")
    }
}
