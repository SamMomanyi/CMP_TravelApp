package com.codewithfk.presentation.feature.checkout

import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.BookingAvailability
import com.codewithfk.domain.model.PaymentIntent
import com.codewithfk.domain.model.TravelListing

data class CheckoutUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val listing: TravelListing? = null,
    val selectedTripDateId: String? = null,
    val numberOfGuests: Int = 1,
    val isCheckingAvailability: Boolean = false,
    val availabilityErrorMessage: String? = null,
    val bookingAvailability: BookingAvailability? = null,
    val creatingBooking: Boolean = false,
    val paymentIntent: PaymentIntent? = null,
)
