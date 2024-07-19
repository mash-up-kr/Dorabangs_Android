package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.OnBoardingKeywords

interface OnBoardingRepository {

    suspend fun getOnboardingKeywords(limit: Int? = null): OnBoardingKeywords
}