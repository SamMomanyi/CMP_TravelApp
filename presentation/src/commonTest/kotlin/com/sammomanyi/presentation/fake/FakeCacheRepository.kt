package com.sammomanyi.presentation.fake

import com.sammomanyi.domain.repository.CacheRepository

class FakeCacheRepository : CacheRepository {
    override suspend fun getAuthToken() = Result.success("fake-token")
    override suspend fun removeAuthToken() = Result.success(true)
}