package com.sammomanyi.presentation.feature.app

import com.sammomanyi.domain.model.TravelListing

data class AppUiState(val isLoading: Boolean = true, val authToken: String? = null)