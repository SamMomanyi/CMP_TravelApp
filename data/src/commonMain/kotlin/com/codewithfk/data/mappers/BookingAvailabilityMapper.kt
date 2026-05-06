package com.codewithfk.data.mappers

import com.codewithfk.data.model.BookingAvailabilityDto
import com.codewithfk.data.model.TripDateDto
import com.codewithfk.domain.model.BookingAvailability
import com.codewithfk.domain.model.TripDate

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