package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllListingUseCase(private val repository: ListingRepository) {
    suspend fun execute(): Result<List<TravelListing>> {
        val data = repository.getAllListings()
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}