package com.sammomanyi.data.mappers

import com.sammomanyi.data.model.BookingDto
import com.sammomanyi.data.model.ListingSummaryDto
import com.sammomanyi.data.model.TripDateSummaryDto
import com.sammomanyi.domain.model.Booking
import com.sammomanyi.domain.model.ListingSummary
import com.sammomanyi.domain.model.TripDateSummary

object ListingSummaryMapper {
    fun toDomain(tripDate: ListingSummaryDto): ListingSummary {

        return ListingSummary(
            images = tripDate.images,
            location = tripDate.location,
            title = tripDate.title
        )
    }

    fun toDomain(tripDates: List<ListingSummaryDto>): List<ListingSummary> {
        return tripDates.map { toDomain(it) }
    }
}