package com.mashup.dorabangs.data.datasource.local.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserLocalDataSource {

    override suspend fun setUserAccessToken(accessToken: String) {
        setDataStore(stringPreferencesKey(ACCESS_TOKEN), accessToken)
    }

    override suspend fun setIsFirstEntry(isFirst: Boolean) {
        setDataStore(booleanPreferencesKey(FIRST_ENTRY), isFirst)
    }

    override suspend fun setLastCopiedUrl(url: String) {
        setDataStore(stringPreferencesKey(LAST_COPIED_URL), url)
    }

    override suspend fun setIdLinkToReadLater(id: String) {
        setDataStore(stringPreferencesKey(READ_LATER_LINK_ID), id)
    }

    override fun getUserAccessToken(): Flow<String> {
        return getDataStore(stringPreferencesKey(ACCESS_TOKEN), "")
    }

    override fun getIsFirstEntry(): Flow<Boolean> {
        return getDataStore(booleanPreferencesKey(FIRST_ENTRY), true)
    }

    override fun getLastCopiedUrl(): Flow<String> {
        return getDataStore(stringPreferencesKey(LAST_COPIED_URL), "")
    }

    override fun getIdFromLinkToReadLater(): Flow<String> {
        return getDataStore(stringPreferencesKey(READ_LATER_LINK_ID), "")
    }

    override suspend fun setNeedToUpdateData(needToUpdate: Boolean) {
        setDataStore(booleanPreferencesKey(NEED_TO_UPDATE_DATA), needToUpdate)
    }

    override fun getNeedToUpdateData(): Flow<Boolean> {
        return getDataStore(booleanPreferencesKey(NEED_TO_UPDATE_DATA), false)
    }

    private suspend fun <T> setDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun <T> getDataStore(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val FIRST_ENTRY = "FIRST_ENTRY"
        private const val LAST_COPIED_URL = "LAST_COPIED_URL"
        private const val READ_LATER_LINK_ID = "READ_LATER_LINK_ID"
        private const val NEED_TO_UPDATE_DATA = "NEED_TO_UPDATE_DATA"
    }
}
