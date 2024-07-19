package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.OnBoardingRemoteDataSource
import com.mashup.dorabangs.data.network.service.OnBoardingService
import javax.inject.Inject

class OnBoardingRemoteDataSourceImpl @Inject constructor(
    private val service: OnBoardingService,
) : OnBoardingRemoteDataSource {

    override suspend fun getOnboardingKeywords(limit: Int?) =
        service.getOnboardingKeywords(limit)
}