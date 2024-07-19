package com.mashup.dorabangs.data.datasource.remote.api

interface OnBoardingRemoteDataSource {

    suspend fun getOnboardingKeywords(limit: Int? = null): List<String>
}