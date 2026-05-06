package com.codewithfk.data.mappers

import com.codewithfk.data.model.PriceCalculationDto
import com.codewithfk.data.model.TripDateDto
import com.codewithfk.domain.model.PriceCalculation
import com.codewithfk.domain.model.TripDate

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