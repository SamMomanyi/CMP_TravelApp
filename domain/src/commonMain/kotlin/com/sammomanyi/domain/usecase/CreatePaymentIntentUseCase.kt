package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.model.Booking
import com.sammomanyi.domain.model.BookingAvailability
import com.sammomanyi.domain.model.PaymentIntent
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.repository.BookingRepository
import com.sammomanyi.domain.repository.ListingRepository
import com.sammomanyi.domain.repository.PaymentRepository
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