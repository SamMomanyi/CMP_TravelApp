package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.BookingAvailability
import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.repository.BookingRepository
import com.codewithfk.domain.repository.ListingRepository
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