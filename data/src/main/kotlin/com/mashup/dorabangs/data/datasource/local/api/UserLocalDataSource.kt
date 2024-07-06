package com.mashup.dorabangs.data.datasource.local.api

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun setUserAccessToken(accessToken: String)

    suspend fun setIsFirstEntry(isFirst: Boolean)

    fun getUserAccessToken(): Flow<String>

    fun getIsFirstEntry(): Flow<Boolean>
}