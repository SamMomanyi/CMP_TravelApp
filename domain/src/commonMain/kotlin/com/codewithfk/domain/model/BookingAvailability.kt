package com.codewithfk.domain.model

data class BookingAvailability(
    val available: Boolean,
    val reason: String? = null,
    val priceCalculation: PriceCalculation? = null
)