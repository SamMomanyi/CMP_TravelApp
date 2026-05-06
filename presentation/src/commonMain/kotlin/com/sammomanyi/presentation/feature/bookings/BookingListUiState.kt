package com.sammomanyi.presentation.feature.bookings

import com.sammomanyi.domain.model.Booking

data class BookingListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val bookings: List<Booking> = emptyList(),
)
