package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.DeviceToken

interface UserRemoteRepository {

    suspend fun registerUser(deviceToken: DeviceToken): String
}
