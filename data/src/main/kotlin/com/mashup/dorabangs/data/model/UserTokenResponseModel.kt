package com.mashup.dorabangs.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAccessTokenResponseModel(
    val data: AccessTokenDataModel = AccessTokenDataModel(),
    val success: Boolean = false,
)

@Serializable
data class AccessTokenDataModel(
    val accessToken: String = "",
)
