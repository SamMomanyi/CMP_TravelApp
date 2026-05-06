package com.sammomanyi.presentation.feature.details

import com.sammomanyi.domain.model.TravelListing


data class TravelListingDetailsUiState(
    val listing:TravelListing? = null,
    val isLoading:Boolean = false,
    val errorMessage:String? = null
) {

}