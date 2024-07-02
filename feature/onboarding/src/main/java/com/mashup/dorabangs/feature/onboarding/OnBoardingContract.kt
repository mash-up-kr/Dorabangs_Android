package com.mashup.dorabangs.feature.onboarding

data class OnBoardingState(
    val keywords: List<String> = emptyList()
)

sealed class OnBoardingSideEffect {

}