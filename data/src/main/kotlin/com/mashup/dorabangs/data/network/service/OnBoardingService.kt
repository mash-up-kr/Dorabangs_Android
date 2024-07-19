package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.OnboardingKeywordsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface OnBoardingService {

    @GET("onboard")
    fun getOnboardingKeywords(@Query("limit") limit: Int? = null): OnboardingKeywordsResponseModel
}