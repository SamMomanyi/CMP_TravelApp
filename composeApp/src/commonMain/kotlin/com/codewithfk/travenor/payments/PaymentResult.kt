package com.codewithfk.travenor.payments

sealed class PaymentResult {
    data object Success : PaymentResult()
    data class Failure(val errorMessage: String) : PaymentResult()
    data object Cancelled : PaymentResult()
}