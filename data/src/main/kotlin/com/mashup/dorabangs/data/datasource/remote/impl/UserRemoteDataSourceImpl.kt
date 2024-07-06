package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.UserRemoteDataSource
import com.mashup.dorabangs.data.model.DeviceTokenDataModel
import com.mashup.dorabangs.data.network.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserRemoteDataSource {

    override suspend fun registerUser(deviceToken: DeviceTokenDataModel): String =
        userService.registerUser(deviceToken)
            .getData()
            ?.accessToken.orEmpty()
}
