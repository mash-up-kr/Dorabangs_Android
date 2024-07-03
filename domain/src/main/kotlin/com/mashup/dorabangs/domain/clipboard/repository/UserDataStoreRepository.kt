package com.mashup.dorabangs.domain.clipboard.repository

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun setUserAccessToken(accessToken: String)
    fun getUserAccessToken(): Flow<String>
    suspend fun setIsFirstEntry(isFirst: Boolean)
    fun getIsFirstEntry(): Flow<Boolean>
}
