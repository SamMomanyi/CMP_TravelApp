package com.sammomanyi.domain.repository

import com.sammomanyi.domain.model.PaymentIntent

interface PaymentRepository {
    suspend fun createPaymentIntent(
        bookingId: String,
        amount: Double? = null,
        currency: String = "usd"
    ): Result<PaymentIntent>
}