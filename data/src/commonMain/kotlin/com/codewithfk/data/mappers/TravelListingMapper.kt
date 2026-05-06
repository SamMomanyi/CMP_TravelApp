package com.codewithfk.data.mappers

import com.codewithfk.data.model.TravelListingDto
import com.codewithfk.domain.model.TravelListing

object TravelListingMapper {

    fun toDomain(dto: TravelListingDto): TravelListing {
        return TravelListing(
            id = dto.id,
            title = dto.title,
            location = dto.location,
            images = dto.images,
            rating = dto.rating,
            description = dto.description,
            price = dto.price,
            currency = dto.currency,
            category = dto.category,
            vendorId = dto.vendorId,
            city = dto.city,
            country = dto.country,
            capacity = dto.capacity,
            availableFrom = dto.availableFrom,
            availableTo = dto.availableTo,
            amenities = dto.amenities,
            reviewCount = dto.reviewCount,
            isActive = dto.isActive,
            tripDates = dto.tripDates?.let { TripDateMapper.toDomain(it) }
        )
    }

    fun toDomain(dtos: List<TravelListingDto>): List<TravelListing> {
        return dtos.map { toDomain(it) }
    }
}