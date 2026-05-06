package com.sammomanyi.data.mappers

import com.sammomanyi.data.model.BookingDto
import com.sammomanyi.data.model.TripDateSummaryDto
import com.sammomanyi.domain.model.Booking
import com.sammomanyi.domain.model.TripDateSummary

object TripDateSummaryMapper {
    fun toDomain(tripDate: TripDateSummaryDto): TripDateSummary {

        return TripDateSummary(
            endDate = tripDate.endDate,
            startDate = tripDate.startDate
        )
    }

    fun toDomain(tripDates: List<TripDateSummaryDto>): List<TripDateSummary> {
        return tripDates.map { toDomain(it) }
    }
}