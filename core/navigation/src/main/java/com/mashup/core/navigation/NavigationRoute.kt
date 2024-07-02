package com.mashup.core.navigation

sealed class NavigationRoute(val route: String) {
    object HomeScreen : NavigationRoute("home")
    object StorageScreen : NavigationRoute("storage") {
        object StorageDetailScreen : NavigationRoute("storage/detail")
    }
    object SaveLink : NavigationRoute("save/copiedUrl") {
        object SelectFolder : NavigationRoute("save/folder")
    }
}
