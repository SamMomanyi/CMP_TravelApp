package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.model.RegisterModel
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.model.UserModel
import com.sammomanyi.domain.repository.ListingRepository
import com.sammomanyi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegisterUseCase(private val repository: UserRepository) {
    suspend fun execute(request: RegisterModel): Result<UserModel> {
        val data = repository.register(request)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}