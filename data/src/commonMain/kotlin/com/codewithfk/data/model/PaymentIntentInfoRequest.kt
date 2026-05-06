package com.codewithfk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentIntentInfoRequest(
    val bookingId: String
)