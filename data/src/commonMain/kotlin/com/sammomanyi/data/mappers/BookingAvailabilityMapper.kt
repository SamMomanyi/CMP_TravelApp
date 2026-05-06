package com.sammomanyi.data.mappers

import com.sammomanyi.data.model.BookingAvailabilityDto
import com.sammomanyi.data.model.TripDateDto
import com.sammomanyi.domain.model.BookingAvailability
import com.sammomanyi.domain.model.TripDate

object BookingAvailabilityMapper {

    fun toDomain(tripDate: BookingAvailabilityDto): BookingAvailability {

        return BookingAvailability(
            available = tripDate.available,
            reason = tripDate.reason,
            priceCalculation = tripDate.priceCalculation?.let { PriceCalculationMapper.toDomain(it) }
        )
    }

    fun toDomain(tripDates: List<BookingAvailabilityDto>): List<BookingAvailability> {
        return tripDates.map { toDomain(it) }
    }
}