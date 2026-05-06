package com.sammomanyi.data.mappers

import com.sammomanyi.data.model.PriceCalculationDto
import com.sammomanyi.data.model.TripDateDto
import com.sammomanyi.domain.model.PriceCalculation
import com.sammomanyi.domain.model.TripDate

object PriceCalculationMapper {

    fun toDomain(tripDate: PriceCalculationDto?): PriceCalculation? {
        if (tripDate == null)
            return null
        return PriceCalculation(
            subtotal = tripDate.subtotal,
            taxes = tripDate.taxes,
            serviceFee = tripDate.serviceFee,
            total = tripDate.total,
            currency = tripDate.currency,
            numberOfNights = tripDate.numberOfNights,
            numberOfGuests = tripDate.numberOfGuests
        )
    }

    fun toDomain(tripDates: List<PriceCalculationDto>): List<PriceCalculation> {
        return tripDates.map { toDomain(it)!! }
    }
}