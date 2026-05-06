package com.codewithfk.travenor.payments

expect class StripePaymentHandler() {
    fun initialize(publishableKey: String)
    suspend fun processPayment(clientSecret: String): PaymentResult
}