package com.mashup.dorabangs.data.network.service

import retrofit2.http.GET
import retrofit2.http.Query

interface OnBoardingService {

    @GET("onboard")
    suspend fun getOnboardingKeywords(@Query("limit") limit: Int? = null): List<String>
}