package com.mashup.dorabangs.core.summary

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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

fun NavController.navigateToAISummary(navOptions: NavOptions? = null, summaryUiModel: AISummaryUiModel) {
    val summary = Uri.encode(Json.encodeToString(summaryUiModel))
    navigate("${NavigationRoute.AISummaryScreen.route}/summary=$summary", navOptions)
}

fun NavGraphBuilder.aiSummaryNavigation(
    navigateToPopBackStack: () -> Unit,
) {
    composable(
        route = "${NavigationRoute.AISummaryScreen.route}/summary={summary}",
        arguments = listOf(
            navArgument(name = "summary") {
                type = serializableNavType<AISummaryUiModel>()
            },
        ),
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(250),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400),
            )
        },
    ) { navBackStackEntry ->
        val aiSummary = navBackStackEntry.arguments?.bundleSerializable("summary") as AISummaryUiModel?

        aiSummary?.let { summary ->
            AISummaryRoute(
                aiSummaryUiModel = summary,
                navigateToPopBackStack = navigateToPopBackStack,
            )
        }
    }
}
