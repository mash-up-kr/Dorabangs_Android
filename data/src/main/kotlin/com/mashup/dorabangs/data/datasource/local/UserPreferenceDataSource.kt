package com.mashup.dorabangs.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun setUserAccessToken(accessToken: String) {
        setDataStore(stringPreferencesKey(ACCESS_TOKEN), accessToken)
    }

    suspend fun setIsFirstEntry(isFirst: Boolean) {
        setDataStore(booleanPreferencesKey(FIRST_ENTRY), isFirst)
    }

    fun getUserAccessToken(): Flow<String> {
        return getDataStore(stringPreferencesKey(ACCESS_TOKEN), "")
    }

    fun getIsFirstEntry(): Flow<Boolean> {
        return getDataStore(booleanPreferencesKey(FIRST_ENTRY), false)
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
    }
}
