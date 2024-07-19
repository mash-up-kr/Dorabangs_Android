package com.mashup.dorabangs.domain.usecase.onboarding

import com.mashup.dorabangs.domain.repository.OnBoardingRepository
import javax.inject.Inject

class GetOnBoardingKeywordsUseCase @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository,
) {

    suspend operator fun invoke(
        limit: Int? = null
    ) = onBoardingRepository.getOnboardingKeywords(limit)
}