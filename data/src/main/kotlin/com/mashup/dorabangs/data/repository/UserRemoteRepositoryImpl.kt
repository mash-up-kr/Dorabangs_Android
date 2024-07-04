package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.model.asData
import com.mashup.dorabangs.data.network.service.UserService
import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.repository.UserRemoteRepository
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : UserRemoteRepository {

    override suspend fun registerUser(deviceToken: DeviceToken): String =
        userService.registerUser(deviceToken.asData())
            .body()
            ?.data
            ?.accessToken ?: ""
}
