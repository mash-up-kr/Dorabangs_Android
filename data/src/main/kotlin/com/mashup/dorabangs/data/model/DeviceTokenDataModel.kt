package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.DeviceToken
import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenDataModel(
    val deviceToken: String,
)

fun DeviceTokenDataModel.asDomain() = DeviceToken(
    deviceToken = this.deviceToken,
)

fun DeviceToken.asData() = DeviceTokenDataModel(
    deviceToken = this.deviceToken,
)
