package com.mashup.dorabangs.core.webview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToWebView(navOptions: NavOptions? = null, url: String?) {
    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    navigate("${NavigationRoute.WebViewScreen.route}?url=$encodedUrl", navOptions)
}

fun NavGraphBuilder.webViewNavigation(
    navigateToPopBackStack: () -> Unit,
) {
    composable(
        route = "${NavigationRoute.WebViewScreen.route}?url={url}",
        arguments = listOf(
            navArgument(name = "url") {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
    ) { navBackStackEntry ->
        val moveUrl = navBackStackEntry.arguments?.getString("url")
        moveUrl?.let { url ->
            DoraWebView(
                url = url,
                navigateToPopBackStack = navigateToPopBackStack,
            )
        }
    }
}
