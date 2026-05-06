package com.codewithfk.presentation.feature.app

import com.codewithfk.domain.model.TravelListing

data class AppUiState(val isLoading: Boolean = true, val authToken: String? = null)