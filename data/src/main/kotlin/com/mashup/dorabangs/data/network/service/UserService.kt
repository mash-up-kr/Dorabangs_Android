package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.DeviceTokenDataModel
import com.mashup.dorabangs.data.model.DoraResponse
import com.mashup.dorabangs.data.model.UserTokenResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("users")
    suspend fun registerUser(@Body deviceToken: DeviceTokenDataModel): UserTokenResponseModel
}
