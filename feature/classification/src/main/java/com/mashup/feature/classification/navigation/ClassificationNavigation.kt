package com.mashup.feature.classification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.feature.classification.ClassificationRoute

fun NavController.navigateToClassification() = navigate(NavigationRoute.ClassificationScreen.route)

fun NavGraphBuilder.classificationNavigation(
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToWebView: (FeedUiModel.FeedCardUiModel) -> Unit,
) {
    composable(
        route = NavigationRoute.ClassificationScreen.route,
    ) {
        ClassificationRoute(
            onClickBackIcon = onClickBackIcon,
            navigateToHome = navigateToHome,
            navigateToWebView = navigateToWebView,
        )
    }
}
