package com.codewithfk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListingResponse(
    val listings: List<TravelListingDto>,
    val page: Int,
    val pageSize: Int,
    val total: Int
)