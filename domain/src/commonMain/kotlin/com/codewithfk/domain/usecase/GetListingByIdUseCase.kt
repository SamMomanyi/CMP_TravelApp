package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListingByIdUseCase(private val repository: ListingRepository) {
    suspend fun execute(id: String): Result<TravelListing> {
        val data = repository.getListingById(id)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}