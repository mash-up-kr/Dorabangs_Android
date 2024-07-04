package com.mashup.dorabangs.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAccessTokenResponseModel(
    val data: AccessTokenDataModel,
    val success: Boolean,
)

@Serializable
data class AccessTokenDataModel(
    val accessToken: String,
)
