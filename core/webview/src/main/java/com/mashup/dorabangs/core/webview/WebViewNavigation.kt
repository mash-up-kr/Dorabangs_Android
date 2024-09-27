package com.mashup.dorabangs.core.webview

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mashup.core.navigation.NavigationRoute
import com.mashup.core.navigation.bundleSerializable
import com.mashup.core.navigation.serializableNavType
import com.mashup.dorabangs.core.model.AISummaryUiModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavController.navigateToWebView(navOptions: NavOptions? = null, summaryUiModel: AISummaryUiModel?) {
    val summary = Uri.encode(Json.encodeToString(summaryUiModel))
    navigate("${NavigationRoute.WebViewScreen.route}/summary=$summary", navOptions)
}

fun NavGraphBuilder.webViewNavigation(
    navigateToPopBackStack: () -> Unit,
    navigateToAISummary: (AISummaryUiModel) -> Unit,
) {
    composable(
        route = "${NavigationRoute.WebViewScreen.route}/summary={summary}",
        arguments = listOf(
            navArgument(name = "summary") {
                type = serializableNavType<AISummaryUiModel>()
            },
        ),
    ) { navBackStackEntry ->
        val aiSummary = navBackStackEntry.arguments?.bundleSerializable("summary") as AISummaryUiModel?
        aiSummary?.url?.let { url ->
            DoraWebView(
                url = url,
                navigateToPopBackStack = navigateToPopBackStack,
                navigateToAISummary = { navigateToAISummary(aiSummary) },
            )
        }
    }
}
