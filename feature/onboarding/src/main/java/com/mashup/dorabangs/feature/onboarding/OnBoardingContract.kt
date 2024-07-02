package com.mashup.dorabangs.feature.onboarding

data class OnBoardingState(
    val keywords: List<String> = emptyList(),
    val selectedIndex: Set<Int> = emptySet()
)

sealed class OnBoardingSideEffect {
    object NavigateToHome : OnBoardingSideEffect()
}