package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.repository.CacheRepository

class GetAuthTokenUseCase(private val repository: CacheRepository) {
    suspend fun execute(): Result<String> {
        val data = repository.getAuthToken()
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}