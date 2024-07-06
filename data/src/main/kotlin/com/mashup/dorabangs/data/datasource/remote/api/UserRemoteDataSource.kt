package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.DeviceTokenDataModel

interface UserRemoteDataSource {

    suspend fun registerUser(deviceToken: DeviceTokenDataModel): String
}