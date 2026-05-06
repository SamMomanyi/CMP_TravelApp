package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.CacheRepository

class RemoveAuthTokenUseCase(private val repository: CacheRepository) {
    suspend fun execute(): Result<Boolean> {
        val data = repository.removeAuthToken()
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}