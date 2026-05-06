package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.model.BookingAvailability
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.repository.BookingRepository
import com.sammomanyi.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckAvailabilityUseCase(private val repository: BookingRepository) {
    suspend fun execute(
        listingId: String,
        tripDateId: String,
        noOfPeople: Int
    ): Result<BookingAvailability> {
        val data = repository.checkAvailability(listingId, tripDateId, noOfPeople)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}