package com.mashup.dorabangs.data.datasource.local.api

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun setUserAccessToken(accessToken: String)

    suspend fun setIsFirstEntry(isFirst: Boolean)

    suspend fun setLastCopiedUrl(url: String)

    fun getUserAccessToken(): Flow<String>

    fun getIsFirstEntry(): Flow<Boolean>

    fun getLastCopiedUrl(): Flow<String>
}
