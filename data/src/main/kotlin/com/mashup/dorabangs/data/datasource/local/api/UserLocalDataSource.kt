package com.mashup.dorabangs.data.datasource.local.api

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun setUserAccessToken(accessToken: String)
    suspend fun setIsFirstEntry(isFirst: Boolean)
    suspend fun setLastCopiedUrl(url: String)
    suspend fun setIdLinkToReadLater(id: String)
    suspend fun setNeedToUpdateData(needToUpdate: Boolean)
    fun getUserAccessToken(): Flow<String>
    fun getIsFirstEntry(): Flow<Boolean>
    fun getLastCopiedUrl(): Flow<String>
    fun getIdFromLinkToReadLater(): Flow<String>
    fun getNeedToUpdateData(): Flow<Boolean>
}
