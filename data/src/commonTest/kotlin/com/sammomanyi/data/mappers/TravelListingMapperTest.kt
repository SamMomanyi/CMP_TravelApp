package com.sammomanyi.data.mappers

import com.sammomanyi.data.model.TravelListingDto
import com.sammomanyi.data.model.TripDateDto
import kotlin.test.Test
import kotlin.test.assertEquals

class TravelListingMapperTest {
    private val tripDateDto = TripDateDto(
        id = "trip-1", listingId = "listing-1",
        startDate = "2026-06-01", endDate = "2026-06-07",
        availableSpots = 3, currentBookings = 1, maxCapacity = 4,
        isActive = true, createdAt = "2025-01-01T00:00:00Z", updatedAt = "2025-01-01T00:00:00Z"
    )

    private val dto = TravelListingDto(
        id = "listing-1", vendorId = "vendor-1", title = "Santorini Greece",
        description = "Beautiful island", category = "Beach",
        location = "Santorini, Greece", city = "Santorini", country = "Greece",
        price = 299.99, currency = "USD", capacity = 4,
        availableFrom = "2026-06-01", availableTo = "2026-08-31",
        images = listOf("https://example.com/img.jpg"),
        amenities = listOf("WiFi", "Pool"), rating = 4.8, reviewCount = 120,
        isActive = true, createdAt = "2025-01-01T00:00:00Z", updatedAt = "2025-01-01T00:00:00Z",
        tripDates = listOf(tripDateDto)
    )

    @Test
    fun toDomain_should_map_TravelListingDto_to_TravelListing() {
        val domain = TravelListingMapper.toDomain(dto)
        assertEquals(dto.id,domain.id)
        assertEquals(dto.vendorId,domain.vendorId)
        assertEquals(dto.title ,domain.title)
        assertEquals(dto.description ,domain.description)
        assertEquals(dto.category ,domain.category)
        assertEquals(dto.location ,domain.location)
        assertEquals(dto.city ,domain.city)
        assertEquals(dto.country ,domain.country)
        assertEquals(dto.price ,domain.price)
        assertEquals(dto.currency ,domain.currency)
        assertEquals(dto.capacity ,domain.capacity)
        assertEquals(dto.availableFrom ,domain.availableFrom)
        assertEquals(dto.availableTo ,domain.availableTo)
        assertEquals(dto.images ,domain.images)
        assertEquals(dto.amenities ,domain.amenities)
        assertEquals(dto.rating ,domain.rating)
        assertEquals(dto.reviewCount ,domain.reviewCount)
        assertEquals(dto.isActive ,domain.isActive)
        // Additional assertions for tripDates can be added here
    }

}