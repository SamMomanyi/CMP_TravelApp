package com.codewithfk.travenor.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codewithfk.data.datasource.CacheDataSource
import kotlinx.coroutines.flow.first

class DataStoreCacheSource(private val dataStore: DataStore<Preferences>) : CacheDataSource {

    companion object {
        const val KEY_AUTH_TOKEN = "auth_token"
    }

    override suspend fun saveAuthToken(token: String) {
        val key = stringPreferencesKey(KEY_AUTH_TOKEN)
        dataStore.edit { preferences ->
            preferences[key] = token
        }
    }

    override suspend fun getAuthToken(): String {
        val key = stringPreferencesKey(KEY_AUTH_TOKEN)
        val preferences = dataStore.data.first()
        return preferences[key] ?: ""
    }

    override suspend fun removeAuthToken(): Result<Boolean> {
        val key = stringPreferencesKey(KEY_AUTH_TOKEN)
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
        return Result.success(true)
    }
}