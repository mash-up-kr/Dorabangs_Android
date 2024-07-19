package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.OnBoardingKeywords

data class OnboardingKeywordsResponseModel(
    val keywords: List<String> = emptyList(),
)

fun OnboardingKeywordsResponseModel.toDomain() =
    OnBoardingKeywords(
        keywords = this.keywords,
    )