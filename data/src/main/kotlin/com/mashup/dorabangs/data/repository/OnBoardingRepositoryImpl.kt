package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.remote.api.OnBoardingRemoteDataSource
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.domain.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val remoteDataSource: OnBoardingRemoteDataSource,
) : OnBoardingRepository {

    override suspend fun getOnboardingKeywords(limit: Int?) =
        remoteDataSource.getOnboardingKeywords(limit)
            .toDomain()
}