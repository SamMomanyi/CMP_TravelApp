package com.codewithfk.domain.repository

interface CacheRepository {
    suspend fun getAuthToken(): Result<String>
    suspend fun removeAuthToken(): Result<Boolean>
}