package com.mashup.dorabangs.domain.repository

interface OnBoardingRepository {

    suspend fun getOnboardingKeywords(limit: Int? = null): List<String>
}