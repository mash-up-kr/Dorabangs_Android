package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.local.UserPreferenceDataSource
import kotlinx.coroutines.flow.Flow
import repository.UserDataStoreRepository
import javax.inject.Inject

class UserDataStoreRepositoryImpl @Inject constructor(
    private val dataSource: UserPreferenceDataSource,

) : UserDataStoreRepository {
    override suspend fun setUserAccessToken(accessToken: String) {
        dataSource.setUserAccessToken(accessToken = accessToken)
    }

    override fun getUserAccessToken(): Flow<String> {
        return dataSource.getUserAccessToken()
    }

    override suspend fun setIsFirstEntry(isFirst: Boolean) {
        dataSource.setIsFirstEntry(isFirst = isFirst)
    }

    override fun getIsFirstEntry(): Flow<Boolean> {
        return dataSource.getIsFirstEntry()
    }
}
