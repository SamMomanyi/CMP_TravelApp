package com.sammomanyi.presentation.feature.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammomanyi.domain.usecase.GetAllBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingListViewModel(private val bookingUseCase: GetAllBookingUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingListUiState())
    val uiState = _uiState.asStateFlow()



    init {
        getAllBookings()
    }

    fun getAllBookings() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            val bookings = bookingUseCase.execute()
            if (bookings.isFailure) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    errorMessage = bookings.exceptionOrNull()?.message ?: "Failed to load bookings"
                )
            } else {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    bookings = bookings.getOrNull()!!
                )
            }
        }
    }


}