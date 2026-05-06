package com.codewithfk.presentation.fake

import com.codewithfk.domain.model.TravelListing
import com.codewithfk.domain.repository.ListingRepository

class FakeListingRepository: ListingRepository {

    var allListing : Result<List<TravelListing>> = Result.success(emptyList())
    var listingById : Result<TravelListing?> = Result.success(null)

    override suspend fun getAllListings() = allListing

    override suspend fun getListingById(id: String) = listingById
}


val fakeListing = TravelListing(
    id = "listing-1", vendorId = "vendor-1", title = "Santorini Greece",
    description = "Beautiful island", category = "Beach",
    location = "Santorini, Greece", city = "Santorini", country = "Greece",
    price = 299.99, currency = "USD", capacity = 4,
    availableFrom = "2026-06-01", availableTo = "2026-08-31",
    images = listOf("https://example.com/img.jpg"),
    amenities = listOf("WiFi", "Pool"), rating = 4.8, reviewCount = 120,
    isActive = true, tripDates = null
)