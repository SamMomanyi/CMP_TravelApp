package com.sammomanyi.data.repository

import com.sammomanyi.data.datasource.CacheDataSource
import com.sammomanyi.domain.repository.CacheRepository

class CacheRepositoryImpl(private val cacheDataSource: CacheDataSource) : CacheRepository{
    override suspend fun getAuthToken(): Result<String> {
        return Result.success(cacheDataSource.getAuthToken())
    }

    override suspend fun removeAuthToken(): Result<Boolean> {
        return cacheDataSource.removeAuthToken()
    }

}