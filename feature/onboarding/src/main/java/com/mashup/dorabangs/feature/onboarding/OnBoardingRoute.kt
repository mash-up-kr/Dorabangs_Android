package com.mashup.dorabangs.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OnBoardingRoute(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {},
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.NavigateToHome -> {
                navigateToHome.invoke()
            }
        }
    }

    OnBoardingScreen(
        modifier = modifier,
        state = state,
        onClickKeyword = viewModel::onClickKeyword,
        onClickOkButton = viewModel::onClickOkButton,
    )
}
