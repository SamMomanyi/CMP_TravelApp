package com.sammomanyi.domain.repository

import com.sammomanyi.domain.model.TravelListing
import kotlinx.coroutines.flow.Flow

interface ListingRepository {
    suspend fun getAllListings(): Result<List<TravelListing>>
    suspend fun getListingById(id: String): Result<TravelListing?>
}