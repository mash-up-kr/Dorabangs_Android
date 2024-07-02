package com.dorabangs.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dorabangs.feature.save.DoraLinkSaveRoute
import com.mashup.core.navigation.NavigationRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToSaveLink(navOptions: NavOptions? = null, copiedUrl: String) {
    val encodedUrl = URLEncoder.encode(copiedUrl, StandardCharsets.UTF_8.toString())
    navigate("${NavigationRoute.SaveLink.route}/$encodedUrl", navOptions)
}

fun NavGraphBuilder.saveLinkNavigation(navController: NavHostController) {
    composable(
        route = "${NavigationRoute.SaveLink.route}/{copiedUrl}",
        arguments = listOf(
            navArgument("copiedUrl") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) { entry ->
        DoraLinkSaveRoute(
            copiedUrl = entry.arguments?.getString("copiedUrl").orEmpty(),
            onClickBackIcon = {
                navController.popBackStack()
            },
            onClickSaveButton = {
                navController.navigateToSaveLinkSelectFolder()
            },
        )
    }
}
