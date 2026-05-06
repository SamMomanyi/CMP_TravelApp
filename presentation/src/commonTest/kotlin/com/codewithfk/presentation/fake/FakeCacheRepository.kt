package com.codewithfk.presentation.fake

import com.codewithfk.domain.repository.CacheRepository

class FakeCacheRepository : CacheRepository {
    override suspend fun getAuthToken() = Result.success("fake-token")
    override suspend fun removeAuthToken() = Result.success(true)
}