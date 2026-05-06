package com.codewithfk.presentation.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.usecase.GetAllListingUseCase
import com.codewithfk.domain.usecase.GetListingByIdUseCase
import com.codewithfk.presentation.feature.listings.TravelListingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TravelListingDetailsViewModel(
    val getListingDetailsUseCase: GetListingByIdUseCase,
    val itemID: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(TravelListingDetailsUiState())
    val state = _uiState.asStateFlow()

    init {
        getListingDetails()
    }

    private fun getListingDetails() {
        viewModelScope.launch {
            _uiState.value = state.value.copy(isLoading = true)
            val item = getListingDetailsUseCase.execute(itemID)
            if (item.isFailure) {
                _uiState.value =
                    state.value.copy(isLoading = false, errorMessage = item.exceptionOrNull()?.message ?: "Error loading details")
            } else {
                _uiState.value = state.value.copy(isLoading = false, listing = item.getOrNull())
            }
        }
    }

}