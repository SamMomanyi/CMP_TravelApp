package com.codewithfk.data.mappers

import com.codewithfk.data.model.BookingDto
import com.codewithfk.data.model.ListingSummaryDto
import com.codewithfk.data.model.TripDateSummaryDto
import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.ListingSummary
import com.codewithfk.domain.model.TripDateSummary

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