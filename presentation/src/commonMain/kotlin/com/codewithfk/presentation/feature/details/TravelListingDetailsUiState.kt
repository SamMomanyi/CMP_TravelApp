package com.codewithfk.presentation.feature.details

import com.codewithfk.domain.model.TravelListing


data class TravelListingDetailsUiState(
    val listing:TravelListing? = null,
    val isLoading:Boolean = false,
    val errorMessage:String? = null
) {

}