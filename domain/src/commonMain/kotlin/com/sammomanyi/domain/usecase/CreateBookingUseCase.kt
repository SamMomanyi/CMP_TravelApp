package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.model.Booking
import com.sammomanyi.domain.model.BookingAvailability
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.repository.BookingRepository
import com.sammomanyi.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend fun execute(
        listingId: String,
        tripDateId: String,
        noOfPeople: Int
    ): Result<Booking> {
        val data = repository.createBooking(listingId, tripDateId, noOfPeople)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}