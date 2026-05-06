package com.sammomanyi.presentation.feature.listings

sealed class TravelListingNavigation {
    data object GoToLogin : TravelListingNavigation()
}