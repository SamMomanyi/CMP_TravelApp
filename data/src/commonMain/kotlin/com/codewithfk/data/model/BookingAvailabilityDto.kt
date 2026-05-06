package com.codewithfk.data.model

@kotlinx.serialization.Serializable
data class BookingAvailabilityDto(
    val available: Boolean,
    val reason: String? = null,
    val priceCalculation: PriceCalculationDto? = null
)