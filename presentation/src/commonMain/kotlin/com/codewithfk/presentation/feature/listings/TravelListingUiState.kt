package com.codewithfk.presentation.feature.listings

import com.codewithfk.domain.AppError
import com.codewithfk.domain.model.TravelListing


data class TravelListingUiState(
    val listings:List<TravelListing> = emptyList(),
    val isLoading:Boolean = false,
    val errorMessage: AppError? = null
) {
    val hasListings: Boolean
        get() = listings.isNotEmpty()

    val showEmptyState: Boolean
        get() = !isLoading && !hasListings && errorMessage == null

}