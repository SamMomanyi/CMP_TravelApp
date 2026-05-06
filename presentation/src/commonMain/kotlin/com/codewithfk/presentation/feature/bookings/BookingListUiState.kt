package com.codewithfk.presentation.feature.bookings

import com.codewithfk.domain.model.Booking

data class BookingListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val bookings: List<Booking> = emptyList(),
)
