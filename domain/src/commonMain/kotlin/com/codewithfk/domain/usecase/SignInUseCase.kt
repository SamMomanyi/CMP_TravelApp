package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.model.UserModel
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SignInUseCase(private val repository: UserRepository) {
    suspend fun execute(userName: String, password: String): Result<UserModel> {
        val data = repository.login(userName, password)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}