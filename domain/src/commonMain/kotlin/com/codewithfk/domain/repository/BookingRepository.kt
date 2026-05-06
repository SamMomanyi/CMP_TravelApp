package com.codewithfk.domain.repository

import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.BookingAvailability

interface BookingRepository {
    suspend fun checkAvailability(
        listingId: String,
        tripDateID: String,
        numberOfGuests: Int
    ): Result<BookingAvailability>

    suspend fun createBooking(
        listingId: String,
        tripDateID: String,
        numberOfGuests: Int,
        specialRequests: String? = null
    ): Result<Booking>

    suspend fun getBookings(): Result<List<Booking>>
}