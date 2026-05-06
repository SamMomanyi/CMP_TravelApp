package com.codewithfk.data.fake

import com.codewithfk.data.datasource.CacheDataSource

class FakeCacheDataSource (private val fakeToken:String = "fake_token"): CacheDataSource {
    override suspend fun saveAuthToken(token: String) {}

    override suspend fun getAuthToken(): String {
        return fakeToken
    }

    override suspend fun removeAuthToken(): Result<Boolean> {
        return Result.success(true)
    }
}