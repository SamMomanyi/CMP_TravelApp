package com.codewithfk.presentation.feature.listings

sealed class TravelListingNavigation {
    data object GoToLogin : TravelListingNavigation()
}