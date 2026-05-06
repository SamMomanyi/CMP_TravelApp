package com.sammomanyi.presentation.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammomanyi.domain.usecase.CheckAvailabilityUseCase
import com.sammomanyi.domain.usecase.CreateBookingUseCase
import com.sammomanyi.domain.usecase.CreatePaymentIntentUseCase
import com.sammomanyi.domain.usecase.GetListingByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    val itemID: String, val getListingByIdUseCase: GetListingByIdUseCase,
    private val checkAvailabilityUseCase: CheckAvailabilityUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val createPaymentIntentUseCase: CreatePaymentIntentUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getListingDetails()
    }

    fun getListingDetails() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            val item = getListingByIdUseCase.execute(itemID)
            if (item.isFailure) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    errorMessage = item.exceptionOrNull()?.message ?: "Failed to load item details"
                )
            } else {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    listing = item.getOrNull()
                )
            }
        }
    }

    fun selectTripDate(tripDateId: String) {
        _uiState.value = uiState.value.copy(selectedTripDateId = tripDateId)
        checkAvailability()
    }

    fun addGuest() {
        if (_uiState.value.numberOfGuests < 10) {
            _uiState.value = uiState.value.copy(numberOfGuests = uiState.value.numberOfGuests + 1)
        }
        checkAvailability()
    }

    fun removeGuest() {
        if (_uiState.value.numberOfGuests > 1) {
            _uiState.value = uiState.value.copy(numberOfGuests = uiState.value.numberOfGuests - 1)
        }
        checkAvailability()
    }


    fun checkAvailability() {
        val listing = uiState.value.listing
        val tripDateId = uiState.value.selectedTripDateId
        val numberOfGuests = uiState.value.numberOfGuests

        if (listing == null || tripDateId == null) {
            _uiState.value = uiState.value.copy(
                availabilityErrorMessage = "Please select a trip date",
                bookingAvailability = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                isCheckingAvailability = true,
                availabilityErrorMessage = null,
                bookingAvailability = null
            )

            val availability = checkAvailabilityUseCase.execute(
                listingId = listing.id,
                tripDateId = tripDateId,
                noOfPeople = numberOfGuests
            )

            if (availability.isFailure) {
                _uiState.value = uiState.value.copy(
                    isCheckingAvailability = false,
                    availabilityErrorMessage = availability.exceptionOrNull()?.message
                        ?: "Failed to check availability",
                    bookingAvailability = null
                )
            } else {
                _uiState.value = uiState.value.copy(
                    isCheckingAvailability = false,
                    availabilityErrorMessage = null,
                    bookingAvailability = availability.getOrNull()
                )
            }
        }
    }

    fun createBooking() {
        val listing = uiState.value.listing
        val tripDateId = uiState.value.selectedTripDateId
        val numberOfGuests = uiState.value.numberOfGuests

        if (listing == null || tripDateId == null) {
            _uiState.value = uiState.value.copy(
                availabilityErrorMessage = "Please select a trip date",
                bookingAvailability = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                creatingBooking = true,
                availabilityErrorMessage = null,
                paymentIntent = null
            )

            val booking = createBookingUseCase.execute(
                listingId = listing.id,
                tripDateId = tripDateId,
                noOfPeople = numberOfGuests
            )

            if (booking.isFailure) {
                _uiState.value = uiState.value.copy(
                    creatingBooking = false,
                    availabilityErrorMessage = booking.exceptionOrNull()?.message
                        ?: "Failed to create booking",
                    paymentIntent = null
                )
            } else {

                booking.getOrNull()!!.let {
                    val paymentIntent = createPaymentIntentUseCase.execute(
                        bookingID = it.id,
                        amount = it.totalPrice,
                        currency = it.currency
                    )
                    if (paymentIntent.isFailure) {
                        _uiState.value = uiState.value.copy(
                            creatingBooking = false,
                            availabilityErrorMessage = paymentIntent.exceptionOrNull()?.message
                                ?: "Failed to create payment intent",
                            paymentIntent = null
                        )
                    } else {
                        _uiState.value = uiState.value.copy(
                            creatingBooking = false,
                            availabilityErrorMessage = null,
                            paymentIntent = paymentIntent.getOrNull()!!
                        )
                    }
                }
            }
        }
    }

}