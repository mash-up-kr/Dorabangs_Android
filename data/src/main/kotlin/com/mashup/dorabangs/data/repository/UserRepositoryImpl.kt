package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import com.mashup.dorabangs.data.datasource.remote.api.UserRemoteDataSource
import com.mashup.dorabangs.data.model.asData
import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun setUserAccessToken(accessToken: String) {
        userLocalDataSource.setUserAccessToken(accessToken = accessToken)
    }

    override fun getUserAccessToken(): Flow<String> {
        return userLocalDataSource.getUserAccessToken()
    }

    override suspend fun setIsFirstEntry(isFirst: Boolean) {
        userLocalDataSource.setIsFirstEntry(isFirst = isFirst)
    }

    override fun getIsFirstEntry(): Flow<Boolean> {
        return userLocalDataSource.getIsFirstEntry()
    }

    override suspend fun registerDeviceToken(deviceToken: DeviceToken): String {
        return userRemoteDataSource.registerUser(deviceToken.asData())
    }
}
