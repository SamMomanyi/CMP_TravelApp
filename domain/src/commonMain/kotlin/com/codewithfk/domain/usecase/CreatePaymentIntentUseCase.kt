package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.BookingAvailability
import com.codewithfk.domain.model.PaymentIntent
import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.repository.BookingRepository
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CreatePaymentIntentUseCase(private val repository: PaymentRepository) {
    suspend fun execute(
        bookingID: String,
        amount: Double,
        currency: String
    ): Result<PaymentIntent> {
        val data = repository.createPaymentIntent(bookingID,amount,currency)
        if (data.isSuccess) {
            return Result.success(data.getOrNull()!!)
        } else {
            return Result.failure(data.exceptionOrNull()!!)
        }
    }
}