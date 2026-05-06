package com.codewithfk.domain.repository

import com.codewithfk.domain.model.PaymentIntent

interface PaymentRepository {
    suspend fun createPaymentIntent(
        bookingId: String,
        amount: Double? = null,
        currency: String = "usd"
    ): Result<PaymentIntent>
}