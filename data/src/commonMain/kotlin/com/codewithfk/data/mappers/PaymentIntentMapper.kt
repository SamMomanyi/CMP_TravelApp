package com.codewithfk.data.mappers

import com.codewithfk.data.model.PaymentIntentDto
import com.codewithfk.domain.model.PaymentIntent

object PaymentIntentMapper {
    fun toDomain(tripDate: PaymentIntentDto): PaymentIntent {

        return PaymentIntent(
            clientSecret = tripDate.clientSecret,
            paymentIntentId = tripDate.paymentIntentId,
            amount = tripDate.amount,
            currency = tripDate.currency,
            status = tripDate.status
        )
    }

    fun toDomain(tripDates: List<PaymentIntentDto>): List<PaymentIntent> {
        return tripDates.map { toDomain(it) }
    }
}