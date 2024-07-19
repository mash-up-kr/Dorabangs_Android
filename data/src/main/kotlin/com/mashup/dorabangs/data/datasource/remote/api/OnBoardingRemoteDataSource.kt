package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.OnboardingKeywordsResponseModel

interface OnBoardingRemoteDataSource {

    suspend fun getOnboardingKeywords(limit: Int? = null): OnboardingKeywordsResponseModel
}