package com.codewithfk.data.mappers

import com.codewithfk.data.model.BookingDto
import com.codewithfk.data.model.TripDateSummaryDto
import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.TripDateSummary

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