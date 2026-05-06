package com.codewithfk.travenor.cache

import com.codewithfk.data.datasource.CacheDataSource
import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

class InMemoryCacheSource : CacheDataSource {
    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    override suspend fun saveAuthToken(token: String) {
        localStorage[KEY_AUTH_TOKEN] = token
    }

    override suspend fun getAuthToken():String {
      return localStorage[KEY_AUTH_TOKEN]!!
    }

    override suspend fun removeAuthToken(): Result<Boolean> {
        localStorage.removeItem(KEY_AUTH_TOKEN)
        return Result.success(true)
    }
}