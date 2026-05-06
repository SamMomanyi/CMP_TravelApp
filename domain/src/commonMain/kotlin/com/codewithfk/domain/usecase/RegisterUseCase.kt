package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.RegisterModel
import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.model.UserModel
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.repository.UserRepository
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