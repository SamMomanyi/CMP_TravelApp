package com.sammomanyi.payments

object PaymentSheetBridge {
    var initializeFunction : ((String) -> Unit)? = null
    var processPaymentFunction : ((String, (String) -> Unit) -> Unit)? = null
}