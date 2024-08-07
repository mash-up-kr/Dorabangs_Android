package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.DeviceToken
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>
    suspend fun setIsFirstEntry(isFirst: Boolean)
    fun getIsFirstEntry(): Flow<Boolean>
    suspend fun registerDeviceToken(deviceToken: DeviceToken): String
    suspend fun setLastCopiedUrl(url: String)
    fun getLastCopiedUrl(): Flow<String>
    suspend fun setIdLinkToReadLater(id: String)
    fun getIdFromLinkToReadLater(): Flow<String>
    suspend fun setNeedToUpdateData(needToUpdate: Boolean)
    fun getNeedToUpdateData(): Flow<Boolean>
}
